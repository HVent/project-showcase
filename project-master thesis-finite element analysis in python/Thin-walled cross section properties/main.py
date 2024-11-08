# -*- coding: utf-8 -*-

import PySimpleGUI as sg
import numpy as np

import auxiliary_variables
import moments_of_area
import warping_and_torsion
import output


PROGRAM_INSTITUTE = """Universitaet Rostock : Fakultaet fuer Maschinenbau und Schiffstechnik
Lehrstuhl fuer Windenergietechnik"""
PROGRAM_NAME = 'Thin-Walled Cross Section Properties'
PROGRAM_VERSION = '17.11.2022'
PROGRAM_AUTHORS = ('Heinrich Vent',)


def input_menu():
    layout = [[sg.Text('Input File'),
               sg.InputText(size=(32, 1), enable_events=True,
                            key='-INPUT_FILEPATH-'),
               sg.FileBrowse(initial_folder=(), button_text='Browse',
                             file_types=(('Text', '.txt'),))
               ], [sg.Button('Solve'), sg.Exit()]
              ]
    height_launcher, width_launcher = 32, 32
    window_menu = sg.Window(PROGRAM_NAME, layout, margins=(height_launcher,
                                                           width_launcher))
    while True:
        event, values = window_menu.read()
        if event == 'Exit' or event == sg.WIN_CLOSED:
            break
        elif event == 'Solve':
            input_filepath = values['-INPUT_FILEPATH-']
            NODE_START_NUMBER = 1
            ELEMENT_START_NUMBER = 1
            NUM_NULL = 1e-6

            USER, CROSS_SECTION_NAME, NK, NM, KNOKO, STAB, DICKE \
                = read_data(input_filepath)

            GMOD = np.ones(NM, dtype=np.float64)

            Ka, Ke, Y_column, Z_column, Ya, Za, Ye, Ze, LANGE, sina, cosa, \
                element_indice = auxiliary_variables.determine_auxiliary_variables(
                    NODE_START_NUMBER, NM, STAB, KNOKO)

            A_i, A, A_qyi, A_qy, A_qzi, A_qz, A_y, A_z, A_yyi, A_yy, A_zzi, \
                A_zz, A_yzi, A_yz, Ys, Zs, A_yyS, A_zzS, A_yzS = \
                moments_of_area.calc_moments_area(DICKE, Ya, Za, Ye, Ze, LANGE,
                                                  sina, cosa)

            Phi, Phi_grad, I1, I2, i1, i2 = moments_of_area.calc_main_system(
                NUM_NULL, A, A_yyS, A_zzS, A_yzS)

            OM, TS, IT_i, IT, OMS, YmS, ZmS, Ym, Zm, OMM, A_wwMi, A_wwM = \
                warping_and_torsion.calc_warping_torsion(NUM_NULL, GMOD, NK,
                                                         NM, KNOKO, DICKE, Ka, Ke, Y_column,
                                                         Z_column, element_indice, Ya, Za, Ye, Ze,
                                                         LANGE, A, A_yyS, A_zzS, A_yzS, Ys, Zs)

            output.save_results_text(PROGRAM_INSTITUTE, PROGRAM_VERSION,
                                     PROGRAM_AUTHORS,
                                     PROGRAM_NAME, input_filepath, NODE_START_NUMBER,
                                     ELEMENT_START_NUMBER, USER, CROSS_SECTION_NAME,
                                     NK, NM, KNOKO, DICKE, Ka, Ke, Y_column, Z_column,
                                     element_indice, A, A_qy, A_qz, A_y, A_z, A_yy,
                                     A_zz, A_yz, Ys, Zs, A_yyS, A_zzS, A_yzS, Phi_grad,
                                     I1, I2, i1, i2, OM, TS, IT, OMS, YmS, ZmS, Ym, Zm,
                                     OMM, A_wwM)
            output.output_menu(USER, CROSS_SECTION_NAME, NODE_START_NUMBER, NK,
                               NM, KNOKO, Ka, Ke, Y_column, Z_column, Ya, Ye,
                               Za, Ze, element_indice, sina, cosa, Ym, Zm, Ys,
                               Zs, OM, OMS, OMM)
            break
    window_menu.close()


def read_data(path):
    ROWS_HEADER_FILE = 3
    ROWS_HEADER_NKNM = 2
    ROWS_VALUES_NKNM = 1
    ROWS_HEADER_KNOKO = 3
    ROWS_HEADER_STAB = 2
    COLUMN_NK = 0
    COLUMN_NM = 1
    COLUMNS_STAB = (0, 1)
    COLUMN_DICKE = 2

    USER = np.loadtxt(path, dtype=str, skiprows=0, max_rows=1)

    CROSS_SECTION_NAME = np.loadtxt(path, dtype=str, skiprows=1, max_rows=1)
    CROSS_SECTION_NAME = str(CROSS_SECTION_NAME)

    rows_until_nknm = ROWS_HEADER_FILE + ROWS_HEADER_NKNM
    NK_NM = np.loadtxt(path, dtype=np.int32, skiprows=rows_until_nknm,
                       max_rows=ROWS_VALUES_NKNM)
    NK = NK_NM[COLUMN_NK]
    NM = NK_NM[COLUMN_NM]

    rows_until_knoko = ROWS_HEADER_FILE + ROWS_HEADER_NKNM + \
        ROWS_VALUES_NKNM + ROWS_HEADER_KNOKO
    KNOKO = np.loadtxt(path, skiprows=rows_until_knoko, max_rows=NK)

    rows_until_stab = ROWS_HEADER_FILE + \
        ROWS_HEADER_NKNM + ROWS_VALUES_NKNM + \
        ROWS_HEADER_KNOKO+NK + ROWS_HEADER_STAB
    STAB = np.loadtxt(path, dtype=np.int32, skiprows=rows_until_stab,
                      max_rows=NM, usecols=COLUMNS_STAB)
    DICKE = np.loadtxt(path, dtype=np.float64, skiprows=rows_until_stab,
                       max_rows=NM, usecols=COLUMN_DICKE)
    return USER, CROSS_SECTION_NAME, NK, NM, KNOKO, STAB, DICKE


if __name__ == '__main__':
    input_menu()
