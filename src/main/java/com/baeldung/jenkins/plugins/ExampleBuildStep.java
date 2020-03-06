package com.baeldung.jenkins.plugins;

import com.google.common.collect.ImmutableSet;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import org.codehaus.groovy.runtime.MethodClosure;
import org.jenkinsci.plugins.workflow.cps.steps.LoadStep;
import org.jenkinsci.plugins.workflow.steps.*;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;

public class ExampleBuildStep extends Step {

    private String input;

    private Object object;


  //  @DataBoundConstructor
    public ExampleBuildStep(Object object) {
        this.object = object;
    }

    @Override
    public StepExecution start(StepContext stepContext) throws Exception {
        return new Execution(stepContext, this);
    }

    private final static class Execution extends SynchronousNonBlockingStepExecution<Object> {

        private transient final ExampleBuildStep step;

        protected Execution(
                @Nonnull StepContext context,
                ExampleBuildStep step) {
            super(context);
            this.step = step;
        }

        @Override
        protected Object run() throws Exception {

            FilePath workspace = getContext().get(FilePath.class);
            workspace.mkdirs();
            return             step.perform(
                    getContext().get(Run.class),
                    workspace,
                    getContext().get(Launcher.class),
                    getContext().get(TaskListener.class),
                    getContext().get(EnvVars.class));
        }
    }

    private Object perform(Run run, FilePath workspace, Launcher launcher, TaskListener taskListener, EnvVars envVars) {
/*        taskListener.getLogger().println("Custom Step ---!==== \n"
                + input + "\n"
                + workspace + "\n"
                + envVars.entrySet().stream().map(es -> es.getKey() + " : " + es.getValue() + "\n").reduce((s1, s2) -> s1 + s2)
                + "\n\n\n"
        );*/
        Map<String, Object> map = (Map<String, Object>) object;
        Map<String, Object> testMap = (Map<String, Object>) map.get("test");
      //  MethodClosure methodClosure = (MethodClosure) testMap.get("unit");
     //   methodClosure.invokeMethod("unitTests", null);

        return testMap;
    }

//    @Extension
    public static class DescriptorImpl extends StepDescriptor {

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return ImmutableSet.of(FilePath.class, Run.class, Launcher.class, TaskListener.class, EnvVars.class);
        }

        @Override
        public String getFunctionName() {
            return "customStep";
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types
            return true;
        }

        public String getDisplayName() {
            return "Example of custom step plugin";
        }
    }
}
