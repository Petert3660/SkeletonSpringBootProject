package com.ptconsultancy.admin.adminsupport;

public class BuildVersion {

    public static String getBuildVersion() {
        return BuildVersion.class.getPackage().getImplementationVersion();
    }
}
