package com.momentary.galaxy.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class DutyCSV {
    @CsvBindByName(required = true, column = "門市代碼")
    @CsvBindByPosition(position=0)
    private String bcId;
    
    @CsvBindByName(required = true, column = "值星機電話")
    @CsvBindByPosition(position=1)
    private String dutyTel;
    
    @CsvBindByName(required = true, column = "匯入結果")
    @CsvBindByPosition(position=2)
    private String uploadResult;
}
