package entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Patient
{
    private Integer ageOfAtTimeOfOperation;
    private Integer positiveAxillaryNodesCount;
    private Integer yearOfOperation;
    private SurvivalStatus survivalStatus;

    public Integer getAgeOfAtTimeOfOperation()
    {
        return ageOfAtTimeOfOperation;
    }

    public void setAgeOfAtTimeOfOperation(Integer ageOfAtTimeOfOperation)
    {
        this.ageOfAtTimeOfOperation = ageOfAtTimeOfOperation;
    }

    public Integer getPositiveAxillaryNodesCount()
    {
        return positiveAxillaryNodesCount;
    }

    public void setPositiveAxillaryNodesCount(Integer positiveAxillaryNodesCount)
    {
        this.positiveAxillaryNodesCount = positiveAxillaryNodesCount;
    }

    public Integer getYearOfOperation()
    {
        return yearOfOperation;
    }

    public void setYearOfOperation(Integer yearOfOperation)
    {
        this.yearOfOperation = yearOfOperation;
    }

    public SurvivalStatus getSurvivalStatus()
    {
        return survivalStatus;
    }

    public void setSurvivalStatus(SurvivalStatus survivalStatus)
    {
        this.survivalStatus = survivalStatus;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    public enum SurvivalStatus
    {
        IN_5_YEAR_OR_LONGER(1),
        IN_5_YEAR(2);

        private int val;

        SurvivalStatus(int val)
        {
            this.val = val;
        }

        public int getVal()
        {
            return val;
        }
    }
}
