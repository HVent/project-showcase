# -*- coding: utf-8 -*-

import numpy as np

from auxiliary_variables import assign_node_coordinates


def assign_OM_K(NM, K, element_indice, OM):
    OM_K = np.zeros(NM, dtype=np.float64)
    for i in element_indice:
        OM_K[i] = OM[K[i]]
    return OM_K


def calc_warping_torsion(NUM_NULL, GMOD, NK, NM, KNOKO, DICKE,
                         Ka, Ke, Y_column, Z_column, element_indice, Ya, Za,
                         Ye, Ze, LANGE, A, A_yyS, A_zzS, A_yzS, Ys, Zs):

    def calc_RT(NUM_NULL, Ya, Za, Ye, Ze, LANGE):
        RT = (Ya*(Ze-Za) - Za*(Ye-Ya)) / LANGE
        for rt in RT:
            if np.abs(rt) < NUM_NULL:
                rt = 0.0
        return RT

    RT = calc_RT(NUM_NULL, Ya, Za, Ye, Ze, LANGE)

    def calc_OM(GMOD, NK, NM, DICKE, Ka, Ke, element_indice, LANGE, RT):

        def calc_B(GMOD, NK, DICKE, Ka, Ke, element_indice, RT):
            B = np.zeros(NK, dtype=np.float64)

            def calc_P_entry(GMOD, DICKE, RT, i):
                P_entry = GMOD[i] * DICKE[i] * RT[i]
                return P_entry

            for i in element_indice:
                B[Ka[i]] += -calc_P_entry(GMOD, DICKE, RT, i)
                B[Ke[i]] += calc_P_entry(GMOD, DICKE, RT, i)
            return B

        B = calc_B(GMOD, NK, DICKE, Ka, Ke, element_indice, RT)

        def calc_S(GMOD, NK, DICKE, Ka, Ke, element_indice, LANGE, RT):
            S = np.zeros((NK, NK), dtype=np.float64)

            def calc_K_entry(GMOD, DICKE, LANGE, i):
                K_entry = GMOD[i] * DICKE[i] / LANGE[i]
                return K_entry

            for i in element_indice:
                S[Ka[i], Ka[i]] += calc_K_entry(GMOD, DICKE, LANGE, i)
                S[Ka[i], Ke[i]] += -calc_K_entry(GMOD, DICKE, LANGE, i)
                S[Ke[i], Ka[i]] += -calc_K_entry(GMOD, DICKE, LANGE, i)
                S[Ke[i], Ke[i]] += calc_K_entry(GMOD, DICKE, LANGE, i)
            return S

        S = calc_S(GMOD, NK, DICKE, Ka, Ke, element_indice, LANGE, RT)

        def calc_OMEG(NK, B, S):
            BB = B[1:NK]
            AA = S[1:NK, 1:NK]
            AA_inv = np.linalg.inv(AA)
            OMEG = np.dot(AA_inv, BB)
            return OMEG

        OMEG = calc_OMEG(NK, B, S)
        OM = np.insert(OMEG, 0, 0)

        def calc_OM_difference(NM, Ka, Ke, element_indice, OM):
            OM_difference = np.zeros(NM, dtype=np.float64)
            for i in element_indice:
                OM_difference[i] = OM[Ka[i]] - OM[Ke[i]]
            return OM_difference

        OM_difference = calc_OM_difference(NM, Ka, Ke, element_indice, OM)

        def calc_OM_mean(NM, Ka, Ke, element_indice, OM):
            OM_mean = np.zeros(NM, dtype=np.float64)
            for i in element_indice:
                OM_mean[i] = (OM[Ka[i]] + OM[Ke[i]]) / 2
            return OM_mean

        OM_mean = calc_OM_mean(NM, Ka, Ke, element_indice, OM)
        return OM, OM_difference, OM_mean

    OM, OM_difference, OM_mean = calc_OM(GMOD, NK, NM, DICKE, Ka, Ke,
                                         element_indice, LANGE, RT)

    def calc_TS(NUM_NULL, DICKE, element_indice, LANGE, RT, OM_difference):

        TS = DICKE * (OM_difference/LANGE + RT)
        for i in element_indice:
            if abs(TS[i]) < NUM_NULL:
                TS[i] = 0.0
        return TS

    TS = calc_TS(NUM_NULL, DICKE, element_indice, LANGE, RT, OM_difference)

    def calc_IT(DICKE, LANGE, RT, TS):

        def calc_IT_closed(LANGE, RT, TS):
            IT_closed_i = RT * TS * LANGE
            IT_closed = np.sum(IT_closed_i)
            return IT_closed_i, IT_closed

        IT_closed_i, IT_closed = calc_IT_closed(LANGE, RT, TS)

        def calc_IT_open(DICKE, LANGE):
            IT_open_i = 1/3 * np.power(DICKE, 3) * LANGE
            IT_open = np.sum(IT_open_i)
            return IT_open_i, IT_open

        IT_open_i, IT_open = calc_IT_open(DICKE, LANGE)

        def calc_IT_total(IT_closed_i, IT_closed, IT_open_i, IT_open):
            IT_i = IT_closed_i + IT_open_i
            IT = IT_closed + IT_open
            return IT_i, IT

        IT_i, IT = calc_IT_total(IT_closed_i, IT_closed, IT_open_i, IT_open)
        return IT_i, IT

    IT_i, IT = calc_IT(DICKE, LANGE, RT, TS)

    def calc_OMS(NK, NM, KNOKO, DICKE, Ka, Ke, Y_column, Z_column,
                 element_indice, LANGE, A, Ys, Zs, OM, OM_mean):

        def calc_A_w(DICKE, Ka, Ke, LANGE, OM_mean):
            A_wi = DICKE * LANGE * OM_mean
            A_w = np.sum(A_wi)
            return A_w

        A_w = calc_A_w(DICKE, Ka, Ke, LANGE, OM_mean)

        def calc_OM_0(A, A_w):
            OM_0 = -A_w / A
            return OM_0

        OM_0 = calc_OM_0(A, A_w)

        def calc_OMS_values(NK, KNOKO, Y_column, Z_column, Ys, Zs, OM, OM_0):
            OMS = np.zeros(NK, dtype=np.float64)
            for k in np.arange(NK):
                OMS[k] = KNOKO[k, Y_column] * Zs - KNOKO[k, Z_column] * Ys \
                    + OM[k] + OM_0
            return OMS

        OMS = calc_OMS_values(NK, KNOKO, Y_column, Z_column, Ys, Zs, OM, OM_0)

        OMS_Ka = assign_OM_K(NM, Ka, element_indice, OMS)
        OMS_Ke = assign_OM_K(NM, Ke, element_indice, OMS)
        return OMS, OMS_Ka, OMS_Ke

    OMS, OMS_Ka, OMS_Ke = calc_OMS(NK, NM, KNOKO, DICKE, Ka, Ke, Y_column,
                                   Z_column, element_indice, LANGE,
                                   A, Ys, Zs, OM, OM_mean)

    def convert_coord_S(NK, NM, KNOKO, Ka, Ke, Y_column, Z_column, element_indice,
                        Ys, Zs):

        def calc_KNOKO_S(NK, KNOKO, Y_column, Z_column, Ys, Zs):
            KNOKO_S = np.zeros((NK, 2), dtype=np.float64)
            for k in np.arange(NK):
                KNOKO_S[k, Y_column] = KNOKO[k, Y_column] - Ys
                KNOKO_S[k, Z_column] = KNOKO[k, Z_column] - Zs
            return KNOKO_S

        KNOKO_S = calc_KNOKO_S(NK, KNOKO, Y_column, Z_column, Ys, Zs)

        YaS = assign_node_coordinates(
            NM, KNOKO_S, Ka, Y_column, element_indice)
        ZaS = assign_node_coordinates(
            NM, KNOKO_S, Ka, Z_column, element_indice)
        YeS = assign_node_coordinates(
            NM, KNOKO_S, Ke, Y_column, element_indice)
        ZeS = assign_node_coordinates(
            NM, KNOKO_S, Ke, Z_column, element_indice)
        return KNOKO_S, YaS, ZaS, YeS, ZeS

    KNOKO_S, YaS, ZaS, YeS, ZeS = convert_coord_S(NK, NM, KNOKO, Ka, Ke,
                                                  Y_column, Z_column,
                                                  element_indice, Ys, Zs)

    def calc_M(DICKE, Ka, Ke, LANGE, Ys, Zs, A_yyS, A_zzS, A_yzS, OMS_Ka, OMS_Ke,
               YaS, ZaS, YeS, ZeS):

        def calc_A_coord_w(DICKE, coord_aS, coord_eS, OMS_Ka, OMS_Ke):
            A_coord_wi = 1/6 * LANGE * DICKE * (coord_aS * (2*OMS_Ka+OMS_Ke)
                                                + coord_eS * (OMS_Ka+2*OMS_Ke))
            return A_coord_wi

        A_ywi = calc_A_coord_w(DICKE, YaS, YeS, OMS_Ka, OMS_Ke)
        A_yw = np.sum(A_ywi)
        A_zwi = calc_A_coord_w(DICKE, ZaS, ZeS, OMS_Ka, OMS_Ke)
        A_zw = np.sum(A_zwi)

        def calc_M_S(A_yyS, A_zzS, A_yzS, A_yw, A_zw):
            YmS = (-A_yzS*A_yw + A_yyS*A_zw) / \
                (A_zzS*A_yyS - np.power(A_yzS, 2))
            ZmS = (-A_zzS*A_yw + A_yzS*A_zw) / \
                (A_zzS*A_yyS - np.power(A_yzS, 2))
            return YmS, ZmS

        YmS, ZmS = calc_M_S(A_yyS, A_zzS, A_yzS, A_yw, A_zw)

        def calc_M_O(coord_mS, coord_S):
            coord_m = coord_mS + coord_S
            return coord_m

        Ym = calc_M_O(YmS, Ys)
        Zm = calc_M_O(ZmS, Zs)
        return YmS, ZmS, Ym, Zm

    YmS, ZmS, Ym, Zm = calc_M(DICKE, Ka, Ke, LANGE, Ys, Zs, A_yyS, A_zzS, A_yzS,
                              OMS_Ka, OMS_Ke, YaS, ZaS, YeS, ZeS)

    def calc_OMM(NM, Ka, Ke, Y_column, Z_column, element_indice,
                 OMS, KNOKO_S, YmS, ZmS):
        OMM = OMS + KNOKO_S[:, Y_column]*ZmS - KNOKO_S[:, Z_column]*YmS

        OMM_Ka = assign_OM_K(NM, Ka, element_indice, OMM)
        OMM_Ke = assign_OM_K(NM, Ke, element_indice, OMM)
        return OMM, OMM_Ka, OMM_Ke

    OMM, OMM_Ka, OMM_Ke = calc_OMM(NM, Ka, Ke, Y_column, Z_column,
                                   element_indice, OMS, KNOKO_S, YmS, ZmS)

    def calc_A_wwM(NM, DICKE, Ka, Ke, element_indice, LANGE, OMM_Ka, OMM_Ke):
        A_wwMi = 1/3 * LANGE * DICKE * (np.power(OMM_Ka, 2) + np.power(OMM_Ke, 2)
                                        + OMM_Ka * OMM_Ke)
        A_wwM = np.sum(A_wwMi)
        return A_wwMi, A_wwM

    A_wwMi, A_wwM = calc_A_wwM(NM, DICKE, Ka, Ke, element_indice, LANGE,
                               OMM_Ka, OMM_Ke)
    return OM, TS, IT_i, IT, OMS, YmS, ZmS, Ym, Zm, OMM, A_wwMi, A_wwM
