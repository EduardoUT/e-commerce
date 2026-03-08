package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.FiscalData;

public class FiscalDatas {
    public static FiscalData.FiscalDataBuilder aFiscalData() {
        return FiscalData.aFiscalData()
                .withRfc("KICR630120NX3")
                .withRegimeTax("05C")
                .withFiscalAddress("Some test fiscal address");
    }
}
