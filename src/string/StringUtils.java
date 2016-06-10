/*
 * FILE NAME: StringUtils.java
 *
 * DESCRIPTION:
 *
 *   Source code for class StringUtils.java
 *
 * COPYRIGHT:
 *   (C) Copyright International Business Machines Corporation 1997, 2014.
 *   Licensed Material - Property of IBM - All Rights Reserved.
 *
 * CLASSIFICATION:
 *   IBM Confidential
 *
 * COMPONENT:
 *   IBM Archive and Essence Manager / IBM LTFS Storage Manager
 *
 * CHANGES:
 *
 *  $XX 08.08.2014 initial version (alexsahm).
 */

package string;

public class StringUtils {

    public static boolean isStringNullOrEmpty (String string)
    {
        return (string == null || string.trim().isEmpty()) ? true : false;
    }

}
