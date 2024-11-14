# -*- coding: utf-8 -*-

import numpy as np


def calc_moments_area(DICKE, Ya, Za, Ye, Ze, LANGE, sina, cosa):
    
    def calc_A_i(DICKE, LANGE):
        A_i = DICKE * LANGE
        return A_i
    
    A_i = calc_A_i(DICKE, LANGE)
    A = np.sum(A_i)

    def calc_A_shear_i(angle_func, A_i):
        A_shear_i = A_i * np.abs(angle_func) 
        return A_shear_i

    A_qyi = calc_A_shear_i(cosa, A_i)
    A_qy = np.sum(A_qyi)
    A_qzi = calc_A_shear_i(sina, A_i)
    A_qz = np.sum(A_qzi)
    
    def calc_A_first_i(coord_a, LANGE, angle_func, A_i):
        A_first_i = A_i * (coord_a + LANGE/2 * angle_func)
        return A_first_i
    
    A_yi = calc_A_first_i(Ya, LANGE, cosa, A_i)
    A_y = np.sum(A_yi)
    A_zi = calc_A_first_i(Za, LANGE, sina, A_i)
    A_z = np.sum(A_zi)
    
    def calc_A_second_i(coord_a, coord_e, A_i):
        A_second_i = A_i * (pow(coord_a, 2) + pow(coord_e, 2) + 
                            coord_a * coord_e) / 3 
        return A_second_i
    
    A_yyi = calc_A_second_i(Ya, Ye, A_i)
    A_yy = np.sum(A_yyi)
    A_zzi = calc_A_second_i(Za, Ze, A_i)
    A_zz = np.sum(A_zzi)
    
    def calc_A_deviation_i(Ya, Za, LANGE, sina, cosa, A_i):
        A_deviation_i = A_i * (Ya*Za + LANGE/2*(Za*cosa + Ya*sina) 
                               + (np.power(LANGE, 2)/3*cosa*sina))
        return A_deviation_i
    
    A_yzi = calc_A_deviation_i(Ya, Za, LANGE, sina, cosa, A_i)
    A_yz = np.sum(A_yzi)
    
    def calc_coord_S(A_first, A):
        coord_S = A_first / A 
        return coord_S
    
    Ys = calc_coord_S(A_y, A)
    Zs = calc_coord_S(A_z, A)
    
    def calc_A_second_S(A, A_second, coord_S):
        A_second_S = A_second - np.power(coord_S, 2) * A
        return A_second_S
    
    A_yyS = calc_A_second_S(A, A_yy, Ys)
    A_zzS = calc_A_second_S(A, A_zz, Zs) 
    
    def calc_A_deviation_S(A, A_deviation, Ys, Zs):
        A_deviation_S = A_deviation - Ys * Zs * A 
        return A_deviation_S
    
    A_yzS = calc_A_deviation_S(A, A_yz, Ys, Zs)
    return A_i, A, A_qyi, A_qy, A_qzi, A_qz, A_y, A_z, A_yyi, A_yy, \
           A_zzi, A_zz, A_yzi, A_yz, Ys, Zs, A_yyS, A_zzS, A_yzS


def calc_main_system(NUM_NULL, A, A_yyS, A_zzS, A_yzS):
    s = 0
    
    def term_for_s(A_yyS, A_zzS, A_yzS):
        term_for_s = (A_yyS-A_zzS) / (2*A_yzS) 
        return term_for_s
    
    if np.abs(A_yzS) > NUM_NULL:
        if term_for_s(A_yyS, A_zzS, A_yzS) > 0:
            s = 1
        elif np.abs(term_for_s(A_yyS, A_zzS, A_yzS)) <= NUM_NULL:
            s = 0
        elif term_for_s(A_yyS, A_zzS, A_yzS):
            s = -1
    
    Phi = 0
    if np.abs(A_yzS) <= NUM_NULL:
        Phi = 0 
    elif np.abs(A_yyS-A_zzS) <= NUM_NULL and np.abs(A_yzS):
        Phi = (np.pi/4) 
    elif 2 * np.abs(A_yzS) >= np.abs(A_yyS-A_zzS):
        Phi = 1/2 * (s*(np.pi/2) - np.arctan((A_yyS-A_zzS) / (2*A_yzS)))
    elif 2 * np.abs(A_yzS) < np.abs(A_yyS-A_zzS):
        Phi = 1/2 * np.arctan((2*A_yzS) / (A_yyS-A_zzS))
    Phi_grad = Phi * 180 / np.pi
    
    def calc_first_term_I(A_yyS, A_zzS):
        first_term_I = 1/2 * (A_yyS+A_zzS)
        return first_term_I
    
    def calc_second_term_I(A_yyS, A_zzS, A_yzS):
        second_term_I = 1/2 * np.sqrt(np.power(A_yyS-A_zzS, 2) 
                                            + 4*np.power(A_yzS, 2))
        return second_term_I
    
    I1 = calc_first_term_I(A_yyS, A_zzS) \
         + calc_second_term_I(A_yyS, A_zzS, A_yzS) 
    I2 = calc_first_term_I(A_yyS, A_zzS) \
         - calc_second_term_I(A_yyS, A_zzS, A_yzS)
         
    def calc_i(A, I):
        i = np.sqrt(I / A)
        return i 
    
    i1 = calc_i(A, I1)
    i2 = calc_i(A, I2)
    
    return Phi, Phi_grad, I1, I2, i1, i2