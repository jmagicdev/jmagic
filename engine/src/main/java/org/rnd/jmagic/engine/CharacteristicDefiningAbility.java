package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/**
 * 604.3. Some static abilities are characteristic-defining abilities. A
 * characteristic-defining ability conveys information about an object's
 * characteristics that would normally be found elsewhere on that object (such
 * as in its mana cost, type line, or power/toughness box).
 * Characteristic-defining abilities function in all zones. They also function
 * outside the game.
 */
public abstract class CharacteristicDefiningAbility extends StaticAbility
{
	private java.util.Collection<Characteristics.Characteristic> characteristics;

	/**
	 * Constructs a characteristic-defining ability that does nothing.
	 * 
	 * @param state The game state in which the ability exists.
	 * @param abilityText The text of the ability.
	 * @param characteristics The copiable characteristics this CDA sets (see
	 * rule 706.8d)
	 */
	public CharacteristicDefiningAbility(GameState state, String abilityText, Characteristics.Characteristic firstCharacteristic, Characteristics.Characteristic... characteristics)
	{
		super(state, abilityText);

		// 604.3. ... Characteristic-defining abilities function in all
		// zones. They also function outside the game.
		this.canApply = NonEmpty.instance();

		this.characteristics = java.util.EnumSet.of(firstCharacteristic, characteristics);
	}

	/**
	 * @return An array of Characteristic constants that this effect affects.
	 */
	@Override
	public java.util.Collection<Characteristics.Characteristic> definesCharacteristics()
	{
		return this.characteristics;
	}
}
