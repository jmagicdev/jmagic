package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Avacyn's Collar")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class AvacynsCollar extends Card
{
	public static final class AvacynsCollarAbility0 extends StaticAbility
	{
		public AvacynsCollarAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+0 and has vigilance.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +1, +0));
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public static final class AvacynsCollarAbility1 extends EventTriggeredAbility
	{
		public AvacynsCollarAbility1(GameState state)
		{
			super(state, "Whenever euipped creature dies, if it was a Human, put a 1/1 white Spirit creature token with flying onto the battlefield.");
			this.addPattern(whenXDies(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator deadCreature = OldObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator itWasAHuman = EvaluatePattern.instance(new SubTypePattern(SubType.HUMAN), deadCreature);
			this.interveningIf = itWasAHuman;

			CreateTokensFactory makeSpirit = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Spirit creature token with flying onto the battlefield");
			makeSpirit.setColors(Color.WHITE);
			makeSpirit.setSubTypes(SubType.SPIRIT);
			makeSpirit.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(makeSpirit.getEventFactory());
		}
	}

	public AvacynsCollar(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+0 and has vigilance.
		this.addAbility(new AvacynsCollarAbility0(state));

		// Whenever equipped creature dies, if it was a Human, put a 1/1 white
		// Spirit creature token with flying onto the battlefield.
		this.addAbility(new AvacynsCollarAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
