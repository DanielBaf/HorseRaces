/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.Reports;

/**
 *
 * @author jefemayoneso
 */
public enum ReportStatus {
    SUCCESS,
    FAILURE,
    UNKNOWN_ACTION,
    FILE_NOT_FOUND,
    LIST_EMPTY,
    VALIDATE_BETS,
    CALCULATE_POINTS,
    SORT_BY_NAME,
    SORT_BY_POINTS,
    SORT_LOW_TO_HIGH,
    SORT_HIGH_TO_LOW,
    SOME_BETS_INVALID
}
