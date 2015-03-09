package droids;

import org.apache.log4j.Logger;

/**
 * Created by Nazar on 21.02.2015.
 */
public class Droid {

    private int damageLevel;       // decrease energyLevel of enemy by its value;
                                  // decrease health by its value
    private int damageRate;
    private int health;
    private int energyLevel; // every 10 point decrease damage received by 1

    private static final Logger LOG = Logger.getLogger(Droid.class);

    public int getDamageRate() {
        return damageRate;
    }

    public void setDamageRate(int damageRate) {
        this.damageRate = damageRate;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    // Shoot

    public void shoot(Droid droid){
        int healthBeforeHitted = droid.getHealth();
        droid.setHealth(droid.getHealth() -
                        (getDamageLevel() - droid.getEnergyLevel() / 10));
        LOG.info("Damage done: " + (healthBeforeHitted - droid.getHealth()) + "\n");
        droid.setEnergyLevel(droid.getEnergyLevel() - getDamageLevel());
        if (droid.getEnergyLevel() == 0) return;
        if (droid.getEnergyLevel() < 0) {
            droid.setEnergyLevel(0);

        }



    }

}
