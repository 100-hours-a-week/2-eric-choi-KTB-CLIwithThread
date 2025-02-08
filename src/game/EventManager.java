package game;

import utill.Constants;
import utill.ThreadedText;

public enum EventManager {
    FESTIVAL("축제가 열렸습니다! 행복도가 증가합니다.") {
        @Override
        public void eventEffect(City city) {
            city.addHappiness(Constants.FESTIVAL_HAPPINESS);
        }
    },

    Environment ("환경 캠페인으로 공해가 감소했습니다.") {
        @Override
        public void eventEffect(City city) {
             city.addPollution(Constants.ENVIRONMENT_CAMPAIGN);
        }
    },

    GOVERNMENT_SUPPLY ("국가 보조금을 받았습니다") {
        @Override
        public void eventEffect(City city) {
            int bonus = (int)(Math.random() * 1000 ) + 500;
            ThreadedText.printText(String.format("\n[이벤트] 국가 보조금 %,d원을 받았습니다!\n", bonus));
            city.addMoney(bonus);
        }
    },

    NATURAL_DISASTER("자연재해가 발생했습니다! 행복도와 자금이 감소합니다.") {
        @Override
        public void eventEffect(City city) {
            city.addHappiness(Constants.DISASTER_HAPPINESS_PENALTY);
            city.addMoney(Constants.DISASTER_MONEY_PENALTY);
        }
    },

    CORPORATE_INVESTMENT("기업 투자 유치! 고용이 증가합니다.") {
        @Override
        public void eventEffect(City city) {
            city.addEmployment(Constants.CORPORATE_INVESTMENT_EMPLOYMENT);
            city.addMoney(Constants.CORPORATE_INVESTMENT_MONEY);
        }
    },

    ECONOMIC_RECESSION("경제 불황! 세수가 감소합니다.") {
        @Override
        public void eventEffect (City city) {
            city.addMoney(Constants.ECONOMIC_RECESSION_MONEY);
            city.addEmployment(Constants.ECONOMIC_RECESSION_EMPLOYMENT);
        }
    };

    private final String script;

    EventManager(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public abstract void eventEffect(City city);

    public static EventManager getRandomEvent() {
        EventManager[] events = values();
        return events[(int)(Math.random() * events.length)];
    }
}
