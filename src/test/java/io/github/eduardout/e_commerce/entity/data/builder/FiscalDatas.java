package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.FiscalData;

public class FiscalDatas {
    public static FiscalData.FiscalDataBuilder aFiscalData() {
        return FiscalData.aFiscalData()
                .withRfc("EKU9003173C9")
                .withRegimeTax("625")
                .withFiscalAddress("Some test fiscal address");
    }
}
