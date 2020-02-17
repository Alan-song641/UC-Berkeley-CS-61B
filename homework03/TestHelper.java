package com.song.homework03;

/* TestHelper.java */

/**
 *  The purpose of this class is to provide a shorthand for writing and testing
 *  invariants in any program.
 **/

public class TestHelper {

    /**
     *  verify() checks an invariant and prints an error message if it fails.
     *  If invariant is true, this method does nothing.  If invariant is false,
     *  the message is printed, followed by a dump of the program stack.
     *
     *  @param invariant  the condition to be verified
     *  @param message  the error message to be printed if the invariant fails to
     *                  hold true.
     **/

    static void verify(boolean invariant, String message) {
        if (!invariant) {
            System.out.println("*** ERROR:  " + message);
            Thread.dumpStack();
        }
    }
}
