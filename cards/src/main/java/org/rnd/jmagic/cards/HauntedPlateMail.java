package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Haunted Plate Mail")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@ColorIdentity({})
public final class HauntedPlateMail extends Card
{
	public static final class HauntedPlateMailAbility0 extends StaticAbility
	{
		public HauntedPlateMailAbility0(GameState state)
		{
			super(state, "Equipped creature gets +4/+4.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +4, +4));
		}
	}

	public static final class HauntedPlateMailAbility1 extends ActivatedAbility
	{
		public HauntedPlateMailAbility1(GameState state)
		{
			super(state, "(0): Until end of turn, Haunted Plate Mail becomes a 4/4 Spirit artifact creature that's no longer an Equipment. Activate this ability only if you control no creatures.");
			this.setManaCost(new ManaPool("(0)"));

			Animator spirit = new Animator(ABILITY_SOURCE_OF_THIS, 4, 4);
			spirit.addType(Type.ARTIFACT);
			spirit.addSubType(SubType.SPIRIT);
			spirit.removeOneSubType(SubType.EQUIPMENT);
			this.addEffect(createFloatingEffect("Until end of turn, Haunted Plate Mail becomes a 4/4 Spirit artifact creature that's no longer an Equipment.", spirit.getParts()));

			this.addActivateRestriction(CREATURES_YOU_CONTROL);
		}
	}

	public HauntedPlateMail(GameState state)
	{
		super(state);

		// Equipped creature gets +4/+4.
		this.addAbility(new HauntedPlateMailAbility0(state));

		// (0): Until end of turn, Haunted Plate Mail becomes a 4/4 Spirit
		// artifact creature that's no longer an Equipment. Activate this
		// ability only if you control no creatures.
		this.addAbility(new HauntedPlateMailAbility1(state));

		// Equip (4) ((4): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
