package de.unijena.bioinf.sirius.gui.compute.jjobs;

import de.unijena.bioinf.ChemistryBase.jobs.SiriusJobs;
import de.unijena.bioinf.jjobs.*;
import de.unijena.bioinf.sirius.Sirius;
import de.unijena.bioinf.sirius.gui.structure.ComputingStatus;
import de.unijena.bioinf.sirius.gui.structure.ExperimentContainer;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class Jobs {
    public static final SwingJobManager MANAGER = (SwingJobManager) SiriusJobs.getGlobalJobManager();

    private static final HashMap<String, Sirius> siriusPerProfile = new HashMap<>();

    private Jobs() {/*prevent instantiation*/}

    public static <JJ extends GuiObservableJJob> JJ submit(final JJ j) {
        submit(j.asSwingJob());
        return j;
    }

    public static <JJ extends SwingJJobContainer> JJ submit(final JJ j) {
        MANAGER.submitSwingJob(j);
        return j;
    }

    public static Sirius getSiriusByProfile(String profile) {
        checkProfile(profile);
        return siriusPerProfile.get(profile);
    }

    public static TinyBackgroundJJob runInBackround(final Runnable task) {
        return SiriusJobs.runInBackround(task);
    }

    public static TinyBackgroundJJob runInBackround(TinyBackgroundJJob task) {
        return SiriusJobs.runInBackround(task);
    }

    public static LoadingBackroundTask runInBackroundAndLoad(final Dialog owner, final Runnable task) {
        return runInBackroundAndLoad(owner, "Please wait", task);
    }

    public static LoadingBackroundTask runInBackroundAndLoad(final Dialog owner, final String title, final Runnable task) {
        return LoadingBackroundTask.runInBackground(owner, title, MANAGER, task);
    }

    public static LoadingBackroundTask runInBackroundAndLoad(final Dialog owner, TinyBackgroundJJob task) {
        return runInBackroundAndLoad(owner, "Please wait", task);
    }

    public static LoadingBackroundTask runInBackroundAndLoad(final Dialog owner, String title, TinyBackgroundJJob task) {
        return LoadingBackroundTask.runInBackground(owner, title, MANAGER, task);
    }

    public static LoadingBackroundTask runInBackroundAndLoad(final JFrame owner, final Runnable task) {
        return runInBackroundAndLoad(owner, "Please wait", task);
    }

    public static LoadingBackroundTask runInBackroundAndLoad(final JFrame owner, final String title, final Runnable task) {
        return LoadingBackroundTask.runInBackground(owner, title, MANAGER, task);
    }

    public static LoadingBackroundTask runInBackroundAndLoad(JFrame owner, TinyBackgroundJJob task) {
        return runInBackroundAndLoad(owner, "Please wait", task);
    }

    public static LoadingBackroundTask runInBackroundAndLoad(JFrame owner, String title, TinyBackgroundJJob task) {
        return LoadingBackroundTask.runInBackground(owner, title, MANAGER, task);
    }

    public static LoadingBackroundTask runInBackroundAndLoad(JFrame owner, String title, boolean indeterminateProgress, TinyBackgroundJJob task) {
        return LoadingBackroundTask.runInBackground(owner, title, indeterminateProgress, MANAGER, task);
    }

    private static void checkProfile(String profile) {
        if (siriusPerProfile.containsKey(profile)) return;
        else try {
            siriusPerProfile.put(profile, new Sirius(profile));
        } catch (IOException | RuntimeException e) {
            LoggerFactory.getLogger(Jobs.class).error("Unknown instrument: '" + profile + "'", e);
            throw new RuntimeException(e);
        }
    }

    public static void cancelALL() {
        //iterator needed to prevent current modification exception
        Iterator<SwingJJobContainer> it = MANAGER.getJobs().iterator();
        while (it.hasNext())
            it.next().getSourceJob().cancel();
    }

    public static void cancel(ExperimentContainer cont) {
        //todo cancel job by container???
    }

    private static final ComputingStatus[] stateMap = {
            ComputingStatus.QUEUED, ComputingStatus.QUEUED, ComputingStatus.QUEUED, ComputingStatus.QUEUED,
            ComputingStatus.COMPUTING, ComputingStatus.UNCOMPUTED, ComputingStatus.FAILED, ComputingStatus.COMPUTED
    };

    public static ComputingStatus getComputingState(JJob.JobState newValue) {
        return stateMap[newValue.ordinal()];
    }
}
