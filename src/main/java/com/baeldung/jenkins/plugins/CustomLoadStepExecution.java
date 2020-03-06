package com.baeldung.jenkins.plugins;

import com.google.inject.Inject;
import groovy.lang.Script;
import hudson.FilePath;
import hudson.model.TaskListener;

import org.jenkinsci.plugins.workflow.cps.CpsFlowExecution;
import org.jenkinsci.plugins.workflow.cps.CpsStepContext;
import org.jenkinsci.plugins.workflow.cps.CpsThread;
import org.jenkinsci.plugins.workflow.cps.replay.ReplayAction;
import org.jenkinsci.plugins.workflow.steps.AbstractStepExecutionImpl;
import org.jenkinsci.plugins.workflow.steps.BodyExecutionCallback;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;

public class CustomLoadStepExecution extends AbstractStepExecutionImpl {


   // @StepContextParameter
    private transient FilePath cwd;

  //  @Inject(optional = true)
    private transient CustomLoadStep customStep;

 //   @StepContextParameter
    private transient TaskListener listener;

    private static final long serialVersionUID = 1L;

    public CustomLoadStepExecution() {
    }

    public boolean start() throws Exception {
            CpsStepContext cps = (CpsStepContext) this.getContext();
            CpsThread t = CpsThread.current();
            CpsFlowExecution execution = t.getExecution();
            String path = customStep.getPath();
            String text = this.cwd.child(path).readToString();
            String clazz = execution.getNextScriptName(path);
            String newText = ReplayAction.replace(execution, clazz);
            if (newText != null) {
                this.listener.getLogger().println("Custom Replacing Groovy text with edited version");
                text = newText;
            }

        Script script = execution.getShell().parse(text);
        cps
                .newBodyInvoker(t.getGroup().export(script))
                .withDisplayName(path)
                .withCallback(BodyExecutionCallback.wrap(cps))
                .start();
        return false;
    }
}
