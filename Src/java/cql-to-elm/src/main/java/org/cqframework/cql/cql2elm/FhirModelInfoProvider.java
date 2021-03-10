package org.cqframework.cql.cql2elm;

import org.hl7.elm.r1.VersionedIdentifier;
import org.hl7.elm_modelinfo.r1.ModelInfo;

import javax.xml.bind.JAXB;

/**
 * Created by Bryn on 4/15/2016.
 */
public class FhirModelInfoProvider implements ModelInfoProvider, NamespaceAware {
    private NamespaceManager namespaceManager;

    public void setNamespaceManager(NamespaceManager namespaceManager) {
        this.namespaceManager = namespaceManager;
    }

    private boolean isFHIRModelIdentifier(VersionedIdentifier modelIdentifier) {
        if (namespaceManager != null && namespaceManager.hasNamespaces()) {
            return modelIdentifier.getId().equalsIgnoreCase("FHIR") &&
                    (modelIdentifier.getSystem() == null || modelIdentifier.getSystem().equals("http://hl7.org/fhir"));
        }

        return modelIdentifier.getId().equalsIgnoreCase("FHIR");
    }

    public ModelInfo load(VersionedIdentifier modelIdentifier) {
        if (isFHIRModelIdentifier(modelIdentifier)) {
            String localVersion = modelIdentifier.getVersion() == null ? "" : modelIdentifier.getVersion();
            switch (localVersion) {
                case "1.0.2":
                    return JAXB.unmarshal(FhirModelInfoProvider.class.getResourceAsStream("/org/hl7/fhir/fhir-modelinfo-1.0.2.xml"),
                            ModelInfo.class);

                case "1.4":
                    return JAXB.unmarshal(FhirModelInfoProvider.class.getResourceAsStream("/org/hl7/fhir/fhir-modelinfo-1.4.xml"),
                            ModelInfo.class);

                case "1.6":
                    return JAXB.unmarshal(FhirModelInfoProvider.class.getResourceAsStream("/org/hl7/fhir/fhir-modelinfo-1.6.xml"),
                            ModelInfo.class);

                case "1.8":
                    return JAXB.unmarshal(FhirModelInfoProvider.class.getResourceAsStream("/org/hl7/fhir/fhir-modelinfo-1.8.xml"),
                            ModelInfo.class);

                case "3.0.0":
                case "":
                    return JAXB.unmarshal(FhirModelInfoProvider.class.getResourceAsStream("/org/hl7/fhir/fhir-modelinfo-3.0.0.xml"),
                            ModelInfo.class);

                case "3.0.1":
                    return JAXB.unmarshal(FhirModelInfoProvider.class.getResourceAsStream("/org/hl7/fhir/fhir-modelinfo-3.0.1.xml"),
                            ModelInfo.class);

                case "3.2.0":
                    return JAXB.unmarshal(FhirModelInfoProvider.class.getResourceAsStream("/org/hl7/fhir/fhir-modelinfo-3.2.0.xml"),
                            ModelInfo.class);

                case "4.0.0":
                    return JAXB.unmarshal(FhirModelInfoProvider.class.getResourceAsStream("/org/hl7/fhir/fhir-modelinfo-4.0.0.xml"),
                            ModelInfo.class);

                case "4.0.1":
                    return JAXB.unmarshal(FhirModelInfoProvider.class.getResourceAsStream("/org/hl7/fhir/fhir-modelinfo-4.0.1.xml"),
                            ModelInfo.class);

                // Do not throw, allow other providers to return the model if known
                //default:
                //    throw new IllegalArgumentException(String.format("Unknown version %s of the FHIR model.", localVersion));
            }
        }

        return null;
    }
}

