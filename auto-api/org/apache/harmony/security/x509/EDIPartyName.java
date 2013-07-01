package org.apache.harmony.security.x509;

// Droidsafe Imports
import droidsafe.helpers.*;
import droidsafe.annotations.*;
import droidsafe.runtime.*;

// needed for enhanced for control translations
import java.util.Iterator;
import org.apache.harmony.security.asn1.ASN1Explicit;
import org.apache.harmony.security.asn1.ASN1Sequence;
import org.apache.harmony.security.asn1.ASN1Type;
import org.apache.harmony.security.asn1.BerInputStream;
import org.apache.harmony.security.x501.DirectoryString;

public final class EDIPartyName {
    @DSGeneratedField(tool_name = "Doppelganger", tool_version = "0.4.2", generated_on = "2013-06-28 14:15:34.831 -0400", hash_original_field = "C7DB6307E9B57AC480C9F2D089C20B85", hash_generated_field = "E531E1267091A670A42BD3F34A2AF2BA")

    private String nameAssigner;
    @DSGeneratedField(tool_name = "Doppelganger", tool_version = "0.4.2", generated_on = "2013-06-28 14:15:34.831 -0400", hash_original_field = "88E24B731AD934BC609FBCD89231B75F", hash_generated_field = "41E7F03877DA6734D1332D3C70A19F95")

    private String partyName;
    @DSGeneratedField(tool_name = "Doppelganger", tool_version = "0.4.2", generated_on = "2013-06-28 14:15:34.831 -0400", hash_original_field = "84BEA1F0FD2CE16F7E562A9F06EF03D3", hash_generated_field = "ACB189C73E1A6432570001B3B9D3D516")

    private byte[] encoding;
    
    @DSGenerator(tool_name = "Doppelganger", tool_version = "0.4.2", generated_on = "2013-06-28 14:15:34.832 -0400", hash_original_method = "2E4835ED3F1B68ED273C9B2B326EFCFA", hash_generated_method = "F696079F2CA56838285735C3A64B1A92")
    private  EDIPartyName(String nameAssigner, String partyName, byte[] encoding) {
        this.nameAssigner = nameAssigner;
        this.partyName = partyName;
        this.encoding = encoding;
        // ---------- Original Method ----------
        //this.nameAssigner = nameAssigner;
        //this.partyName = partyName;
        //this.encoding = encoding;
    }

    
    @DSGenerator(tool_name = "Doppelganger", tool_version = "0.4.2", generated_on = "2013-06-28 14:15:34.832 -0400", hash_original_method = "8CF73AB8FE0E45F61A0A453F52513BE8", hash_generated_method = "9CDDD339577A1DC5D9CDF06B9F77A7E6")
    public byte[] getEncoded() {
        {
            encoding = ASN1.encode(this);
        } //End block
        byte[] var2F9C81BC6E497382285CD6B7A7E33DE1_403418560 = {getTaintByte()};
        return var2F9C81BC6E497382285CD6B7A7E33DE1_403418560;
        // ---------- Original Method ----------
        //if (encoding == null) {
            //encoding = ASN1.encode(this);
        //}
        //return encoding;
    }

    
    @DSGeneratedField(tool_name = "Doppelganger", tool_version = "0.4.2", generated_on = "2013-06-28 14:15:34.832 -0400", hash_original_field = "F09E30973AEF91130C504D57F6F7184E", hash_generated_field = "69033EAAAFF984476D66802FF78AA0DF")

    public static final ASN1Sequence ASN1 = new ASN1Sequence(
            new ASN1Type[] {
                new ASN1Explicit(0, DirectoryString.ASN1),
                new ASN1Explicit(1, DirectoryString.ASN1)
            }) {
        {
            setOptional(0);
        }

        @Override protected Object getDecodedObject(BerInputStream in) {
            Object[] values = (Object[]) in.content;
            return new EDIPartyName((String) values[0], (String) values[1],
                    in.getEncoded());
        }

        @Override protected void getValues(Object object, Object[] values) {
            EDIPartyName epn = (EDIPartyName) object;
            values[0] = epn.nameAssigner;
            values[1] = epn.partyName;
        }
    };
    /*
    // orphaned legacy method
    @Override protected void getValues(Object object, Object[] values) {
            EDIPartyName epn = (EDIPartyName) object;
            values[0] = epn.nameAssigner;
            values[1] = epn.partyName;
        }
    
    // orphaned legacy method
    @Override protected Object getDecodedObject(BerInputStream in) {
            Object[] values = (Object[]) in.content;
            return new EDIPartyName((String) values[0], (String) values[1],
                    in.getEncoded());
        }
    
    */
}

