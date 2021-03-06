/*
 *  This file is part of the SIRIUS library for analyzing MS and MS/MS data
 *
 *  Copyright (C) 2013-2015 Kai Dührkop
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with SIRIUS.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.unijena.bioinf.fingerid.net;

import de.unijena.bioinf.ChemistryBase.fp.MaskedFingerprintVersion;
import de.unijena.bioinf.ChemistryBase.fp.ProbabilityFingerprint;

public class FingerIdJob {

    protected final String name;
    protected final String securityToken;
    protected final long jobId;

    protected String state;
    protected ProbabilityFingerprint prediction;
    protected double[] iokrVerctor;
    protected MaskedFingerprintVersion version;
    protected String errorMessage;


    public FingerIdJob(long jobId, String securityToken, MaskedFingerprintVersion version, String name) {
        this.name = name;
        this.securityToken = securityToken;
        this.jobId = jobId;
        this.version = version;
    }

    public String getState() {
        return state;
    }

    public ProbabilityFingerprint getPrediction() {
        return prediction;
    }
}
