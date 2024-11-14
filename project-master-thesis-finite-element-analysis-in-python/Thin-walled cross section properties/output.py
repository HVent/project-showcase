# -*- coding: utf-8 -*-

import PySimpleGUI as sg
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg, \
    NavigationToolbar2Tk

from datetime import datetime

from warping_and_torsion import assign_OM_K


def save_results_text(PROGRAM_INSTITUTE, PROGRAM_VERSION, PROGRAM_AUTHORS,
                      PROGRAM_NAME, input_filepath, NODE_START_NUMBER,
                      ELEMENT_START_NUMBER, USER, CROSS_SECTION_NAME,
                      NK, NM, KNOKO, DICKE, Ka, Ke, Y_column, Z_column,
                      element_indice, A, A_qy, A_qz, A_y, A_z, A_yy, A_zz,
                      A_yz, Ys, Zs, A_yyS, A_zzS, A_yzS, Phi_grad, I1, I2,
                      i1, i2, OM, TS, IT, OMS, YmS,
                      ZmS, Ym, Zm, OMM, A_wwM):

    def create_output_filepath(input_filepath):
        index_of_point = input_filepath.find('.')
        additional_output_text = '_results'
        output_filepath = input_filepath[:index_of_point] \
            + additional_output_text \
            + input_filepath[index_of_point:]
        return output_filepath

    output_filepath = create_output_filepath(input_filepath)

    def get_date_time():
        date_time = datetime.now()
        date = '%s.%s.%s' % (date_time.day, date_time.month, date_time.year)
        time = '%s:%s:%s' % (
            date_time.hour, date_time.minute, date_time.second)
        return date, time

    date, time = get_date_time()

    header_text_out = \
        '%s\n' % PROGRAM_INSTITUTE \
        + '---------\n' \
        + 'Version: %s\n' % PROGRAM_VERSION \
        + 'Author(s): %s\n\n' % PROGRAM_AUTHORS \
        + 'User: %s\n' % USER \
        + 'Cross section name: %s\n' % CROSS_SECTION_NAME \
        + '---------\n\n' \
        + 'Date: %s\n' % date \
        + 'Time: %s\n\n' % time

    def create_KNOKO_text(NODE_START_NUMBER, NK, KNOKO, Y_column, Z_column):
        KNOKO_text = ''
        for k in np.arange(NK):
            KNOKO_text += '%i: %.3f %.3f\n' % (k+NODE_START_NUMBER,
                                               KNOKO[k, Y_column],
                                               KNOKO[k, Z_column])
        return KNOKO_text

    KNOKO_text = create_KNOKO_text(NODE_START_NUMBER, NK, KNOKO, Y_column,
                                   Z_column)

    def create_STAB_DICKE_text(NODE_START_NUMBER, ELEMENT_START_NUMBER, DICKE,
                               Ka, Ke, element_indice):
        STAB_DICKE_text = ''
        for i in element_indice:
            STAB_DICKE_text += '%i: %i - %i : %.3f\n' % (i+ELEMENT_START_NUMBER,
                                                         Ka[i] +
                                                         NODE_START_NUMBER,
                                                         Ke[i] +
                                                         NODE_START_NUMBER,
                                                         DICKE[i])
        return STAB_DICKE_text

    STAB_DICKE_text = create_STAB_DICKE_text(NODE_START_NUMBER,
                                             ELEMENT_START_NUMBER, DICKE, Ka,
                                             Ke, element_indice)
    replica_input_data = \
        'EINGABEDATEN\n' \
        + '---------\n' \
        + 'Anzahl von Knoten NK / Elementen NM\n' \
        + '%i %i\n' % (NK, NM) \
        + '---------\n' \
        + 'Knotenkoordinaten KNOKO\n' \
        + 'Nr.: Y Z\n' \
        + KNOKO_text \
        + '---------\n' \
        + 'Elemente STAB\n' \
        + 'Nr.: Ka - Ke : DICKE\n' \
        + STAB_DICKE_text \
        + '---------\n' \
        + '\n'

    coordinates_moments_text = \
        'QUERSCHNITTSWERTE\n' \
        + '---------\n' \
        + 'Werte im Ausgangskoordinatensystem (Ursprung O)\n' \
        + '---------\n' \
        + 'Koordinaten Flaechenschwerpunkt FSP S\n' \
        + 'Ys [L] = %.5f\n' % Ys \
        + 'Zs [L] = %.5f\n' % Zs \
        + '\n' \
        + 'Flaecheninhalt A [L^2] = %.2f\n' % A \
        + 'Schubflaeche A_qy [L^2] = %.2f\n' % A_qy \
        + 'Schubflaeche A_qz [L^2] = %.2f\n' % A_qz \
        + 'Statisches Moment A_y [L^3] = %.2f\n' % A_y \
        + 'Statisches Moment A_z [L^3] = %.2f\n' % A_z \
        + 'Traegheitsmoment A_yy [L^4] = %.2f\n' % A_yy \
        + 'Traegheitsmoment A_zz [L^4] = %.2f\n' % A_zz \
        + 'Deviationsmoment A_yz [L^4] = %.2f\n' % A_yz \
        + '\n' \
        + 'Werte im Schwerpunktkoordinatensystem (Ursprung S)\n' \
        + '---------\n' \
        + '\n' \
        + 'Traegheitsmoment A_yyS [L^4] = %.2f\n' % A_yyS \
        + 'Traegheitsmoment A_zzS [L^4] = %.2f\n' % A_zzS \
        + 'Deviationsmoment A_yzS [L^4] = %.2f\n' % A_yzS \
        + '\n' \
        + 'Verdrehwinkel Phi [°] = %.5f\n' % Phi_grad \
        + 'Haupttraegheitsmoment1 I1 [L^4] = %.2f\n' % I1 \
        + 'Haupttraegheitsmoment2 I2 [L^4] = %.2f\n' % I2 \
        + 'Haupttraegheitsradius i1 [L] = %.5f\n' % i1 \
        + 'Haupttraegheitsradius i2 [L] = %.5f\n' % i2 \
        + '\n' \
        + 'Torsionstraegheitsmoment IT [L^4] = %.3f\n' % IT \
        + '---------\n' \
        + 'Koordinaten Schubmittelpunkt SMP M, bezogen auf O\n' \
        + 'Ym [L] = %.5f\n' % Ym \
        + 'Zm [L] = %.5f\n' % Zm \
        + '\n' \
        + 'Koordinaten Schubmittelpunkt SMP M, bezogen auf S\n' \
        + 'YmS [L] = %.5f\n' % YmS \
        + 'ZmS [L] = %.5f\n' % ZmS \
        + '\n' \
        + 'Woelbtraegheitsmoment A_wwM [L^6] = %.2f\n' % A_wwM \
        + '---------\n' \
        + '\n' \


    def create_OM_text(NODE_START_NUMBER, NK, OM, OM_name):
        OM_text = 'Knoten-Nr.: %s [L^2]\n' % OM_name \
            + '---------\n'
        for k in np.arange(NK):
            OM_text += '%i: %.4f\n' % (k+NODE_START_NUMBER, OM[k])
        return OM_text

    OM_text = create_OM_text(NODE_START_NUMBER, NK, OM,
                             'Omega_Ursprung OM')
    OMS_text = create_OM_text(NODE_START_NUMBER, NK, OMS,
                              'Omega_Schwerpkt OMS')
    OMM_text = create_OM_text(NODE_START_NUMBER, NK, OMM,
                              'Omega_Schubmittelpkt OMM')

    warping_text = \
        'Woelbfunktion Omega\n' \
        + '---------\n' \
        + OM_text \
        + '\n' \
        + OMS_text \
        + '\n' \
        + OMM_text \
        + '\n'

    def create_TS_text(NODE_START_NUMBER, ELEMENT_START_NUMBER, Ka, Ke,
                       element_indice, TS):
        TS_text = 'Element : bezogener Schubfluss TS [L^2]\n' \
                  + '---------\n'
        for i in element_indice:
            TS_text += '%i: %i - %i : %.5f\n' % (i+ELEMENT_START_NUMBER, Ka[i]
                                                 + NODE_START_NUMBER, Ke[i] +
                                                 NODE_START_NUMBER, TS[i])
        return TS_text

    TS_text = create_TS_text(NODE_START_NUMBER, ELEMENT_START_NUMBER, Ka, Ke,
                             element_indice, TS)

    output_text = header_text_out + replica_input_data \
        + coordinates_moments_text + warping_text \
        + TS_text

    with open(output_filepath, 'w') as output_text_file:
        output_text_file.write(output_text)


def output_menu(USER, CROSS_SECTION_NAME, NODE_START_NUMBER, NK, NM, KNOKO,
                Ka, Ke, Y_column, Z_column, Ya, Ye, Za, Ze, element_indice,
                sina, cosa, Ym, Zm, Ys, Zs, OM, OMS, OMM):

    WINDOW_SIZE = 600
    ASPECT_RATIO = 1
    SCALE_FACTOR_OM = 100
    X_POSITION_3D = 0

    PLOT_OPTIONS_2D = '2D'
    PLOT_OPTIONS_3D = '3D'
    PLOT_OPTIONS_DIMENSIONS = (PLOT_OPTIONS_2D, PLOT_OPTIONS_3D)

    DATA_OPTIONS_WITHOUT = 'cross section only'
    DATA_OPTIONS_OM = 'OM'
    DATA_OPTIONS_OMS = 'OMS'
    DATA_OPTIONS_OMM = 'OMM'
    PLOT_OPTIONS_DATA = (DATA_OPTIONS_WITHOUT, DATA_OPTIONS_OM, DATA_OPTIONS_OMS,
                         DATA_OPTIONS_OMM)

    def create_figure(CROSS_SECTION_NAME, WINDOW_SIZE, ASPECT_RATIO):
        fig = plt.figure(1, clear=True)
        DPI = fig.get_dpi()
        fig.set_size_inches((WINDOW_SIZE+4) * ASPECT_RATIO / float(DPI),
                            (WINDOW_SIZE+4) / float(DPI))
        plt.title(CROSS_SECTION_NAME)
        return fig

    def configure_2D_figure(fig):
        plt.gca().set_aspect('equal', adjustable='box')
        plt.xlabel('y')
        plt.ylabel('z')
        plt.grid()

    def plot_2D_KNOKO(NODE_START_NUMBER, NK, KNOKO, Y_column, Z_column, fig):
        plt.scatter(KNOKO[:, Y_column], KNOKO[:, Z_column], color='blue')
        for k in np.arange(NK):
            plt.annotate(k+NODE_START_NUMBER, (KNOKO[k, Y_column],
                                               KNOKO[k, Z_column]))

    def plot_2D_elements(Ya, Ye, Za, Ze, element_indice, fig):
        plt.plot((Ya, Ye), (Za, Ze), color='blue')

    def plot_2D_center(Ym, Zm, Ys, Zs, fig):
        def plot_O(fig):
            plt.scatter(0, 0, color='red', label='O')
        plot_O(fig)

        def plot_M(Ym, Zm, fig):
            plt.scatter(Ym, Zm, color='black', label='M = (%.2f, %.2f)'
                        % (Ym, Zm))
            plt.annotate('M', (Ym, Zm))
        plot_M(Ym, Zm, fig)

        def plot_S(Ys, Zs, fig):
            plt.scatter(Ys, Zs, color='green', label='S = (%.2f, %.2f)'
                        % (Ys, Zs))
            plt.annotate('S', (Ys, Zs))
        plot_S(Ys, Zs, fig)

    def plot_2D_OM(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice, sina, cosa,
                   OM, SCALE_FACTOR_OM, OM_legend_text, fig):

        def calc_OM_scal(OM, SCALE_FACTOR_OM):
            OM_scal = OM / SCALE_FACTOR_OM
            return OM_scal

        OM_scal = calc_OM_scal(OM, SCALE_FACTOR_OM)

        def calc_OM_delta(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice,
                          sina, cosa, OM_scal):
            OM_Ka_scal = assign_OM_K(NM, Ka, element_indice, OM_scal)
            OM_Ke_scal = assign_OM_K(NM, Ke, element_indice, OM_scal)

            def calc_OM_interim(sina, cosa, OM_K_scal):
                OM_dy = OM_K_scal * sina
                OM_dz = OM_K_scal * cosa
                return OM_dy, OM_dz
            OM_dy_a, OM_dz_a = calc_OM_interim(sina, cosa, OM_Ka_scal)
            OM_dy_e, OM_dz_e = calc_OM_interim(sina, cosa, OM_Ke_scal)

            def calc_direction(Ya, Za, Ye, Ze, element_indice):
                direction = np.ones(NM)
                for i in element_indice:
                    if Ya[i]*Ze[i] - Ye[i]*Za[i] >= 0:
                        direction[i] = -1
                return direction
            direction = calc_direction(Ya, Za, Ye, Ze, element_indice)

            def combine_OM_direction(OM_d, direction):
                OM_d *= direction
                return OM_d
            OM_dy_a = combine_OM_direction(OM_dy_a, direction)
            OM_dz_a = combine_OM_direction(OM_dz_a, direction)
            OM_dy_e = combine_OM_direction(OM_dy_e, direction)
            OM_dz_e = combine_OM_direction(OM_dz_e, direction)
            return OM_dy_a, OM_dz_a, OM_dy_e, OM_dz_e
        OM_dy_a, OM_dz_a, OM_dy_e, OM_dz_e = calc_OM_delta(NM, Ka, Ke, Ya, Za,
                                                           Ye, Ze, element_indice,
                                                           sina, cosa, OM_scal)

        def calc_OM_Y(Y_node, OM_dy):
            OM_Y = Y_node + OM_dy
            return OM_Y
        OM_Ya = calc_OM_Y(Ya, OM_dy_a)
        OM_Ye = calc_OM_Y(Ye, OM_dy_e)

        def calc_OM_Z(Z_node, OM_dz):
            OM_Z = Z_node - OM_dz
            return OM_Z
        OM_Za = calc_OM_Z(Za, OM_dz_a)
        OM_Ze = calc_OM_Z(Ze, OM_dz_e)

        def plot_2Dlines_OM(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice,
                            OM, OM_Ya, OM_Za, OM_Ye, OM_Ze, OM_legend_text,
                            fig):
            OM_Ka = assign_OM_K(NM, Ka, element_indice, OM)
            OM_Ke = assign_OM_K(NM, Ke, element_indice, OM)
            for i in element_indice:
                if i == 0:
                    plt.plot((Ya[i], OM_Ya[i], OM_Ye[i], Ye[i]),
                             (Za[i], OM_Za[i], OM_Ze[i], Ze[i]),
                             label=OM_legend_text, color='orange')
                    plt.annotate('%.2f' % OM_Ka[i], (OM_Ya[i], OM_Za[i]))
                    plt.annotate('%.2f' % OM_Ke[i], (OM_Ye[i], OM_Ze[i]))
                else:
                    plt.plot((Ya[i], OM_Ya[i], OM_Ye[i], Ye[i]),
                             (Za[i], OM_Za[i], OM_Ze[i], Ze[i]), color='orange')
                    plt.annotate('%.2f' % OM_Ka[i], (OM_Ya[i], OM_Za[i]))
                    plt.annotate('%.2f' % OM_Ke[i], (OM_Ye[i], OM_Ze[i]))
        plot_2Dlines_OM(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice,
                        OM, OM_Ya, OM_Za, OM_Ye, OM_Ze, OM_legend_text, fig)

    def create_3D_plot(fig):
        ax = fig.add_subplot(projection='3d')
        return ax

    def configure_3D_plot(ax):
        ax.set_xlabel('y')
        ax.set_ylabel('x')
        ax.set_zlabel('z')

    def plot_3D_KNOKO(NODE_START_NUMBER, NK, KNOKO, Y_column, Z_column,
                      X_POSITION_3D, ax):
        ax.scatter(KNOKO[:, Y_column], X_POSITION_3D, KNOKO[:, Z_column],
                   color='blue')
        for k in np.arange(NK):
            ax.text(KNOKO[k, Y_column], X_POSITION_3D, KNOKO[k, Z_column],
                    k+NODE_START_NUMBER)

    def plot_3D_elements(Ya, Ye, Za, Ze, element_indice,
                         X_POSITION_3D, ax):
        for i in element_indice:
            ax.plot3D((Ya[i], Ye[i]), (X_POSITION_3D, X_POSITION_3D),
                      (Za[i], Ze[i]), color='blue')

    def plot_3D_center(Ym, Zm, Ys, Zs, X_POSITION_3D, ax):
        def plot_O(X_POSITION_3D, ax):
            ax.scatter(0, X_POSITION_3D, 0, color='red', label='O')
        plot_O(X_POSITION_3D, ax)

        def plot_M(Ym, Zm, X_POSITION_3D, ax):
            ax.scatter(Ym, X_POSITION_3D, Zm, color='black',
                       label='M = (%.2f, %.2f)' % (Ym, Zm))
            ax.text(Ym, X_POSITION_3D, Zm, 'M')
        plot_M(Ym, Zm, X_POSITION_3D, ax)

        def plot_S(Ys, Zs, X_POSITION_3D, ax):
            ax.scatter(Ys, X_POSITION_3D, Zs, color='green',
                       label='S = (%.2f, %.2f)' % (Ys, Zs))
            ax.text(Ys, X_POSITION_3D, Zs, 'S')
        plot_S(Ys, Zs, X_POSITION_3D, ax)

    def plot_3D_OM(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice,
                   OM, X_POSITION_3D, OM_legend_text, ax):
        OM_Ka = assign_OM_K(NM, Ka, element_indice, OM)
        OM_Ke = assign_OM_K(NM, Ke, element_indice, OM)
        for i in element_indice:
            if i == 0:
                ax.plot3D((Ya[i], Ya[i], Ye[i], Ye[i]),
                          (X_POSITION_3D, OM_Ka[i], OM_Ke[i], X_POSITION_3D),
                          (Za[i], Za[i], Ze[i], Ze[i]),
                          label=OM_legend_text, color='orange')
                ax.text(Ya[i], OM_Ka[i], Za[i], '%.2f' % OM_Ka[i])
                ax.text(Ye[i], OM_Ke[i], Ze[i], '%.2f' % OM_Ke[i])
            else:
                ax.plot3D((Ya[i], Ya[i], Ye[i], Ye[i]),
                          (X_POSITION_3D, OM_Ka[i], OM_Ke[i], X_POSITION_3D),
                          (Za[i], Za[i], Ze[i], Ze[i]), color='orange')
                ax.text(Ya[i], OM_Ka[i], Za[i], '%.2f' % OM_Ka[i])
                ax.text(Ye[i], OM_Ke[i], Ze[i], '%.2f' % OM_Ke[i])

    def draw_figure_w_toolbar(canvas, fig, canvas_toolbar):
        if canvas.children:
            for child in canvas.winfo_children():
                child.destroy()
        if canvas_toolbar.children:
            for child in canvas_toolbar.winfo_children():
                child.destroy()
        figure_canvas_agg = FigureCanvasTkAgg(fig, master=canvas)
        figure_canvas_agg.draw()
        toolbar = NavigationToolbar2Tk(figure_canvas_agg, canvas_toolbar)
        toolbar.update()
        figure_canvas_agg.get_tk_widget().pack(side='right', fill='both',
                                               expand=1)

    layout = [
        [sg.Text('Additional data:'),
         sg.DropDown(PLOT_OPTIONS_DATA, default_value=DATA_OPTIONS_OM,
                     readonly=True, key='ADDITIONAL_DATA'),
         sg.Text('Dimensions:'),
         sg.DropDown(PLOT_OPTIONS_DIMENSIONS, default_value=PLOT_OPTIONS_2D,
                     readonly=True, key='DIMENSIONS')],
        [sg.Button('Plot'), sg.Button('Exit')],
        [sg.Canvas(key='controls_cv')],
        [sg.Column(
            layout=[
                [sg.Canvas(key='fig_cv',
                           size=((WINDOW_SIZE+4) * ASPECT_RATIO,
                                 (WINDOW_SIZE+4))
                           )]
            ],
            background_color='#DAE0E6',
            pad=(0, 0)
        )]
    ]
    window = sg.Window('Graph Plot Menu', layout)

    while True:
        event, values = window.read()
        if event in (sg.WIN_CLOSED, 'Exit'):
            break
        elif event == 'Plot':
            fig = create_figure(CROSS_SECTION_NAME, WINDOW_SIZE,
                                ASPECT_RATIO)
            if values['DIMENSIONS'] == PLOT_OPTIONS_2D:
                configure_2D_figure(fig)
                plot_2D_KNOKO(NODE_START_NUMBER, NK, KNOKO, Y_column,
                              Z_column, fig)
                plot_2D_elements(Ya, Ye, Za, Ze, element_indice, fig)
                plot_2D_center(Ym, Zm, Ys, Zs, fig)
                if values['ADDITIONAL_DATA'] == DATA_OPTIONS_OM:
                    plot_2D_OM(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice,
                               sina, cosa, OM, SCALE_FACTOR_OM,
                               DATA_OPTIONS_OM, fig)
                elif values['ADDITIONAL_DATA'] == DATA_OPTIONS_OMS:
                    plot_2D_OM(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice,
                               sina, cosa, OMS, SCALE_FACTOR_OM,
                               DATA_OPTIONS_OMS, fig)
                elif values['ADDITIONAL_DATA'] == DATA_OPTIONS_OMM:
                    plot_2D_OM(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice,
                               sina, cosa, OMM, SCALE_FACTOR_OM,
                               DATA_OPTIONS_OMM, fig)
            elif values['DIMENSIONS'] == PLOT_OPTIONS_3D:
                ax = create_3D_plot(fig)
                configure_3D_plot(ax)
                plot_3D_KNOKO(NODE_START_NUMBER, NK, KNOKO, Y_column,
                              Z_column, X_POSITION_3D, ax)
                plot_3D_elements(Ya, Ye, Za, Ze, element_indice,
                                 X_POSITION_3D, ax)
                plot_3D_center(Ym, Zm, Ys, Zs, X_POSITION_3D, ax)
                if values['ADDITIONAL_DATA'] == DATA_OPTIONS_OM:
                    plot_3D_OM(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice,
                               OM, X_POSITION_3D, DATA_OPTIONS_OM, ax)
                elif values['ADDITIONAL_DATA'] == DATA_OPTIONS_OMS:
                    plot_3D_OM(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice,
                               OMS, X_POSITION_3D, DATA_OPTIONS_OMS, ax)
                elif values['ADDITIONAL_DATA'] == DATA_OPTIONS_OMM:
                    plot_3D_OM(NM, Ka, Ke, Ya, Za, Ye, Ze, element_indice,
                               OMM, X_POSITION_3D, DATA_OPTIONS_OMM, ax)
            fig.legend()
            draw_figure_w_toolbar(window['fig_cv'].TKCanvas, fig,
                                  window['controls_cv'].TKCanvas)
    window.close()
