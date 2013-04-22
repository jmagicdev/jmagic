package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kemba's Legion")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.SOLDIER})
@ManaCost("5WW")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class KembasLegion extends Card
{
	public static final class KembasLegionAbility1 extends StaticAbility
	{
		public KembasLegionAbility1(GameState state)
		{
			super(state, "Kemba's Legion can block an additional creature for each Equipment attached to Kemba's Legion.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CAN_BLOCK_AN_ADDITIONAL_CREATURE);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.NUMBER, Count.instance(Intersect.instance(HasSubType.instance(SubType.EQUIPMENT), AttachedTo.instance(This.instance()))));
			this.addEffectPart(part);
		}
	}

	public KembasLegion(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(6);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Kemba's Legion can block an additional creature for each Equipment
		// attached to Kemba's Legion.
		this.addAbility(new KembasLegionAbility1(state));
	}
}
