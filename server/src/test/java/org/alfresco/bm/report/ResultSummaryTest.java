/*
 * Copyright (C) 2005-2014 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.bm.report;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @see ResultSummary
 * 
 * @author Derek Hulley
 * @since 2.0
 */
public class ResultSummaryTest
{
    @Test
    public void empty()
    {
        ResultSummary summary = new ResultSummary("A");
        Assert.assertEquals(Double.NaN, summary.getSuccessPercentage());
        Assert.assertEquals("A", summary.getName());
        Assert.assertEquals(0, summary.getTotalResults());
        Assert.assertEquals(0, summary.getStats(true).getN());
        Assert.assertEquals(0, summary.getStats(false).getN());
    }

    @Test
    public void illegalArguments()
    {
        ResultSummary summary = new ResultSummary("A");
        try
        {
            summary.addSample(true, -1L);
            Assert.fail("Did not detect negative time");
        }
        catch (IllegalArgumentException e)
        {
            // Expected
        }
    }

    @Test
    public void twoResults()
    {
        ResultSummary summary = new ResultSummary("A");
        summary.addSample(true, 20L);
        summary.addSample(false, 20L);
        Assert.assertEquals(50.0, summary.getSuccessPercentage());
        Assert.assertEquals(2, summary.getTotalResults());
        Assert.assertEquals(1, summary.getStats(true).getN());
        Assert.assertEquals(20.0, summary.getStats(true).getMean());
        Assert.assertEquals(1, summary.getStats(false).getN());
        Assert.assertEquals(20.0, summary.getStats(false).getMean());
    }

    @Test
    public void fourResults()
    {
        ResultSummary summary = new ResultSummary("A");
        summary.addSample(true, 20L);
        summary.addSample(false, 20L);
        summary.addSample(true, 40L);
        summary.addSample(false, 40L);
        Assert.assertEquals(50.0, summary.getSuccessPercentage());
        Assert.assertEquals(4, summary.getTotalResults());
        Assert.assertEquals(2, summary.getStats(true).getN());
        Assert.assertEquals(30.0, summary.getStats(true).getMean());
        Assert.assertEquals(2, summary.getStats(false).getN());
        Assert.assertEquals(30.0, summary.getStats(false).getMean());
    }
}