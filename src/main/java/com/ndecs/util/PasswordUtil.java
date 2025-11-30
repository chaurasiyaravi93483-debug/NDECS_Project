package com.ndecs.util;

public class PasswordUtil {

    /*
       ==========================================================
        CURRENT PROJECT REQUIREMENT:
        -------------------------------------
        - Your database stores plain passwords.
        - So login should work using simple plain comparison.
        - Hashing is kept here for future use (disabled by default).
       ==========================================================
     */

    /**
     * Hash function (currently NOT used)
     * It simply returns the plain password.
     * Later if you want proper security, we can switch to BCrypt here.
     */
    public static String hash(String input) {
        if (input == null) return null;

        // 🔹 CURRENT MODE → return plain password (NO HASH)
        return input;

        // 🔹 FUTURE SECURE MODE (Enable later)
        // return BCrypt.hashpw(input, BCrypt.gensalt());
    }

    /**
     * Verify plain password with stored password.
     * Works for your current project where passwords are not hashed.
     */
    public static boolean verify(String plain, String stored) {

        // Null safety
        if (plain == null || stored == null) {
            return false;
        }

        // Debug logs (you can disable any time)
        System.out.println("Plain Password  = " + plain);
        System.out.println("Stored Password = " + stored);

        // 🔹 CURRENT MODE → simple equals
        return plain.equals(stored);

        /*
        // 🔹 FUTURE SECURE MODE (Enable when DB stores hashed passwords)
        try {
            return BCrypt.checkpw(plain, stored);
        } catch (Exception e) {
            return false;
        }
        */
    }
}
