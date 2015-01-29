package java.io;

// Droidsafe Imports
import droidsafe.runtime.*;
import droidsafe.helpers.*;
import droidsafe.annotations.*;
import java.security.BasicPermission;
import java.security.Permission;

public final class SerializablePermission extends BasicPermission {
    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2013-12-30 12:56:50.141 -0500", hash_original_method = "059CB3A47A80651C55222CAAC7D4899E", hash_generated_method = "3B3AEFDD3879538C3ACCB66AE5192762")
    
public SerializablePermission(String permissionName) { super(""); }

    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2013-12-30 12:56:50.143 -0500", hash_original_method = "817285F0A88BCE83A5485E1175378F5A", hash_generated_method = "9ED12F03DCCCC662269C551824630068")
    
public SerializablePermission(String name, String actions) { super("", ""); }

    @DSSource({DSSourceKind.SENSITIVE_UNCATEGORIZED})
    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2013-12-30 12:56:50.146 -0500", hash_original_method = "1D3A92DE09AD9A3275C68C126C1F0E4E", hash_generated_method = "3D225D3CE47DA77C079F578AAAC6007E")
    
@Override public String getActions() { return null; }

    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2013-12-30 12:56:50.148 -0500", hash_original_method = "15107D2478EB5CA607140B3E4F96BD4D", hash_generated_method = "7028504695C2B07EE227E4A73DB8D42F")
    
@Override public boolean implies(Permission permission) { return true; }
    
}
