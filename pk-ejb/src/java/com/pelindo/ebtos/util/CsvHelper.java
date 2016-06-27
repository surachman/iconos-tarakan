/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.util;

import com.csvreader.CsvWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

/**
 *
 * @author R Seno Anggoro
 */
public class CsvHelper {
    public static InputStream getSptStream(List<Object[]> rows) throws IOException {
        StringWriter outFile = new StringWriter();
        CsvWriter csvOutput = new CsvWriter(outFile, ',');
        for (int i = 0; i < rows.size(); i++) {
            csvOutput.write(rows.get(i)[0].toString());
            csvOutput.write(rows.get(i)[1].toString());
            csvOutput.write(rows.get(i)[2].toString());
            csvOutput.write(rows.get(i)[3].toString());
            csvOutput.write(rows.get(i)[4].toString());
            csvOutput.write(rows.get(i)[5].toString());
            csvOutput.write(rows.get(i)[6].toString());
            csvOutput.write(rows.get(i)[7].toString());
            csvOutput.write(rows.get(i)[8].toString());
            csvOutput.write(rows.get(i)[9].toString());
            csvOutput.write(rows.get(i)[10].toString());
            csvOutput.write(rows.get(i)[11].toString());
            csvOutput.write(rows.get(i)[12].toString());
            csvOutput.write(rows.get(i)[13].toString());
            csvOutput.write(rows.get(i)[14].toString());
            csvOutput.write(rows.get(i)[15].toString());
            csvOutput.write(rows.get(i)[16].toString());
            csvOutput.write(rows.get(i)[17].toString());
            csvOutput.write(rows.get(i)[18].toString());
            csvOutput.endRecord();
        }
        csvOutput.close();
        return new ByteArrayInputStream(outFile.getBuffer().toString().getBytes());
    }
}
