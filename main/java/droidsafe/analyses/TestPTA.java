package droidsafe.analyses;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soot.Context;
import soot.Local;
import soot.MethodOrMethodContext;
import soot.Scene;
import soot.SootMethod;
import soot.Value;
import soot.ValueBox;
import soot.jimple.AnyNewExpr;
import soot.jimple.AssignStmt;
import soot.jimple.CastExpr;
import soot.jimple.InstanceFieldRef;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.NullConstant;
import soot.jimple.Stmt;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.pta.IAllocNode;
import droidsafe.android.system.API;
import droidsafe.utils.SootUtils;
import droidsafe.analyses.pta.PTABridge;

/**
 * Hacked up testing pass for the PTA and the call graph traversal.
 * 
 * @author mgordon
 *
 */
public class TestPTA  {
    /** Logger field */
    private static final Logger logger = LoggerFactory.getLogger(TestPTA.class);

    int numMethods = 0;

    public TestPTA() {
        // TODO Auto-generated constructor stub
        testUserMethods();
    }


    public void visitMethods() {
        for (SootMethod method : PTABridge.v().getReachableMethods()) {
            testMethod(method);
        }
        
        //countMethods(method, context);
    }

    private void countMethods(SootMethod sm, Edge context) {
        numMethods++;
        System.out.println("Call: " + sm);
    }

    private void testUserMethods() {
        for (MethodOrMethodContext momc : PTABridge.v().getReachableMethodContexts()) {
            SootMethod method = momc.method();
            Context context = momc.context();
            
            
            if (!method.getSignature().contains("<java.lang.RealToString: java.lang.RealToString getInstance()"))
                continue;
                      
            if (!method.isConcrete())
                continue;
            if (!method.hasActiveBody()) {
                method.retrieveActiveBody();
            }
            
            Iterator<Edge> edges = Scene.v().getCallGraph().edgesInto(momc); 
            while (edges.hasNext()) {
                System.out.println(edges.next());
            }
            
            System.out.println(momc);
            
            edges = Scene.v().getCallGraph().edgesOutOf(momc); 
            while (edges.hasNext()) {
                System.out.println(edges.next());
            }
            
            // We first gather all the memory access expressions
            for (Iterator stmts = method.getActiveBody().getUnits().iterator(); stmts.hasNext();) {
                Stmt st = (Stmt) stmts.next();
                System.out.println(st);
                for (ValueBox vb : st.getUseAndDefBoxes()) {
                    Value value = vb.getValue();
                    if (PTABridge.v().isPointer(value)) {
                        System.out.println(value);
                        System.out.println("  Context Sensitive");
                        for (IAllocNode node : PTABridge.v().getPTSet(value, context)) {
                            System.out.println("\t" + node);
                        }
                        
                        System.out.println("  Context Insensitive");
                        for (IAllocNode node : PTABridge.v().getPTSet(value)) {
                            System.out.println("\t" + node);
                        }
                    }
                }
                System.out.println();
            }

        }
        
    }
    
    private void testMethod(SootMethod sm) {
        //if (API.v().isSystemMethod(sm))
        //    return;
        
        if (!sm.getSignature().startsWith("<"))
            return;

        numMethods++;
        System.out.println("Test Method: " + sm);

        //System.out.println(sm);
        //System.out.println("  " + context);

        /*System.out.println("context: " + context);
        System.out.println("Edge into: " + edgeInto);
        System.out.println("Context context: " + context.srcCtxt());
        System.out.println("Stmt: " + context.srcStmt());
        System.out.println();
         */

        if (!sm.isConcrete())
            return;
        if (!sm.hasActiveBody()) {
            sm.retrieveActiveBody();
        }


        // We first gather all the memory access expressions
        for (Iterator stmts = sm.getActiveBody().getUnits().iterator(); stmts.hasNext();) {
            Stmt st = (Stmt) stmts.next();

            InstanceInvokeExpr iie = SootUtils.getInstanceInvokeExpr(st);


            System.out.println("Instance Invoke: " + st);
           
            for (IAllocNode node : PTABridge.v().getPTSet(iie.getBase()))
                System.out.println("  " + node);
            System.out.println("Args");
            for (Value val : iie.getArgs()) {
                if (PTABridge.v().isPointer(val)) {
                    for (IAllocNode node : PTABridge.v().getPTSet(val))
                        System.out.println("  " + node);
                } else {
                    System.out.println("  not a pointer.");
                }
            }
            System.out.println();


            if (st instanceof AssignStmt) {

                AssignStmt a = (AssignStmt) st;
                try {
                    if (a.getRightOp() instanceof AnyNewExpr) {
                        if (PTABridge.v().getAllocNode(a.getRightOp(), null) == null)
                            System.out.println("New expr not in map: " + a);
                    } 

                    if (PTABridge.v().isPointer(a.getLeftOp())) {
                        Set<? extends IAllocNode> lhsNodes = PTABridge.v().getPTSet(a.getLeftOp());

                        Set<? extends IAllocNode> rhsNodes = null;
                        if (PTABridge.v().isPointer(a.getRightOp())) {
                            rhsNodes = PTABridge.v().getPTSet(a.getRightOp() 
                                );
                        } else if (PTABridge.v().getAllocNode(a.getRightOp(), null) != null) {
                            Set<IAllocNode> nodes = new LinkedHashSet<IAllocNode>();
                            nodes.add(PTABridge.v().getAllocNode(a.getRightOp(), null));
                            rhsNodes = nodes;
                        } else if (a.getRightOp() instanceof CastExpr) {
                            CastExpr castExpr = (CastExpr)a.getRightOp();
                            rhsNodes = PTABridge.v().getPTSet(castExpr.getOp());
                            Set<IAllocNode> toRemove = new HashSet<IAllocNode>();
                            for (IAllocNode node : rhsNodes) {
                                if (!PTABridge.v().isLegalCast(node.getType(), castExpr.getCastType())) {
                                    toRemove.add(node);
                                }
                            }
                            rhsNodes.removeAll(toRemove);
                        } else if (a.getRightOp() instanceof InvokeExpr || a.getRightOp() instanceof NullConstant) {
                            rhsNodes = new LinkedHashSet<IAllocNode>();
                        } else {
                            rhsNodes = new LinkedHashSet<IAllocNode>();
                            System.out.println("** Empty RHS: " + a.getRightOp());
                        }

                        if (a.getLeftOp() instanceof Local && !lhsNodes.containsAll(rhsNodes)) {
                            System.out.println(sm + "\nAssign: " + st);
                            System.out.println("RHS: " + a.getRightOp() + " " + a.getRightOp().getClass());
                            for (IAllocNode node : rhsNodes) 
                                System.out.println("  " + node);
                            System.out.println("LHS: " + a.getLeftOp() + " " + a.getLeftOp().getClass());
                            for (IAllocNode node : lhsNodes) 
                                System.out.println("  " + node);
                            if (a.getLeftOp() instanceof InstanceFieldRef) {
                                Set<? extends IAllocNode> baseNodes = 
                                        PTABridge.v().getPTSet(
                                            ((InstanceFieldRef)a.getLeftOp()).getBase());
                                for (IAllocNode node : baseNodes) {
                                    System.out.println("  baseNode: " + node);
                                }
                            }
                            /*  System.out.println("Ins LHS: ");
                        for (AllocNode node : PTABridge.v().getPTSetContextIns(a.getLeftOp()))
                            System.out.println("  " + node);
                             */
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error with PTA: {}", st, e);
                    droidsafe.main.Main.exit(1);
                }
            }
        }
    }

}
