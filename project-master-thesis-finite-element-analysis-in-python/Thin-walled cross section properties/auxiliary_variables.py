# -*- coding: utf-8 -*-

import numpy as np


def assign_node_coordinates(NM, KNOKO, node_numbers, axis_column, 
                            element_indice):
    coordinates = np.zeros((NM), dtype=np.float64)
    for i in element_indice:
        coordinates[i] = KNOKO[node_numbers[i], axis_column]
    return coordinates 


def determine_auxiliary_variables(NODE_START_NUMBER, NM, STAB, KNOKO):
    Ka_column = 0 
    Ke_column = 1
    
    def assign_node_numbers(NODE_START_NUMBER, STAB, node_column):
        node_numbers = STAB[:, node_column] - NODE_START_NUMBER
        return node_numbers
        
    Ka = assign_node_numbers(NODE_START_NUMBER, STAB, Ka_column)
    Ke = assign_node_numbers(NODE_START_NUMBER, STAB, Ke_column)
    
    Y_column = 0 
    Z_column = 1
    element_indice = np.arange(NM)
    Ya = assign_node_coordinates(NM, KNOKO, Ka, Y_column, element_indice)
    Za = assign_node_coordinates(NM, KNOKO, Ka, Z_column, element_indice)
    Ye = assign_node_coordinates(NM, KNOKO, Ke, Y_column, element_indice)
    Ze = assign_node_coordinates(NM, KNOKO, Ke, Z_column, element_indice) 
    
    def calc_LANGE(Ya, Za, Ye, Ze):
        LANGE = np.sqrt(np.power(Ye-Ya, 2) + np.power(Ze-Za, 2))
        return LANGE
    
    LANGE = calc_LANGE(Ya, Za, Ye, Ze)
    
    def calc_angle_func(a, e, LANGE):
        angle_func = (e - a) / LANGE
        return angle_func
    
    sina = calc_angle_func(Za, Ze, LANGE)
    cosa = calc_angle_func(Ya, Ye, LANGE)
    return Ka, Ke, Y_column, Z_column, Ya, Za, Ye, Ze, LANGE, sina, cosa, element_indice


