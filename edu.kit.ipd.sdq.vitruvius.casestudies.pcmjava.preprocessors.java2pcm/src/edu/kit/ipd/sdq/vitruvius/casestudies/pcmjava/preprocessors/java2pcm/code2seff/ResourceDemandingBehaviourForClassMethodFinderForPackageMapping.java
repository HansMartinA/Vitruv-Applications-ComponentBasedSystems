package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.preprocessors.java2pcm.code2seff;

import java.util.Set;

import org.apache.log4j.Logger;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil;

public class ResourceDemandingBehaviourForClassMethodFinderForPackageMapping
        implements ResourceDemandingBehaviourForClassMethodFinding {

    private static final Logger logger = Logger
            .getLogger(ResourceDemandingBehaviourForClassMethodFinderForPackageMapping.class.getSimpleName());

    private final CorrespondenceInstance correspondenceInstance;

    public ResourceDemandingBehaviourForClassMethodFinderForPackageMapping(
            final CorrespondenceInstance correspondenceInstance) {
        this.correspondenceInstance = correspondenceInstance;
    }

    @Override
    public ResourceDemandingSEFF getCorrespondingRDSEFForClassMethod(final ClassMethod classMethod) {
        return this.getFirstCorrespondingEObjectIfAny(classMethod, ResourceDemandingSEFF.class);
    }

    private <T> T getFirstCorrespondingEObjectIfAny(final ClassMethod classMethod, final Class<T> correspondingClass) {
        final Set<T> correspondingObjects = CorrespondenceInstanceUtil
                .getCorrespondingEObjectsByType(this.correspondenceInstance, classMethod, correspondingClass);
        if (correspondingObjects == null || correspondingObjects.isEmpty()) {
            return null;
        }
        if (1 < correspondingObjects.size()) {
            logger.warn("Found " + correspondingObjects.size() + " corresponding Objects from Type "
                    + correspondingClass + " for ClassMethod " + classMethod + " Returning the first.");
        }
        return correspondingObjects.iterator().next();
    }

    @Override
    public ResourceDemandingInternalBehaviour getCorrespondingResourceDemandingInternalBehaviour(
            final ClassMethod classMethod) {
        return this.getFirstCorrespondingEObjectIfAny(classMethod, ResourceDemandingInternalBehaviour.class);
    }

}
