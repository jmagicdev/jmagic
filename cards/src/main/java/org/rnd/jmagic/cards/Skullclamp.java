package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skullclamp")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Skullclamp extends Card
{
	public static final class BrainJuicer extends StaticAbility
	{
		public BrainJuicer(GameState state)
		{
			super(state, "Equipped creature gets +1/-1.");

			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +1, -1));
		}
	}

	public static final class WizardsPrintsBrokenCards extends EventTriggeredAbility
	{
		public WizardsPrintsBrokenCards(GameState state)
		{
			super(state, "Whenever equipped creature dies, draw two cards.");

			this.addPattern(whenXDies(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));

			this.addEffect(drawCards(You.instance(), 2, "Draw two cards."));
		}
	}

	public Skullclamp(GameState state)
	{
		super(state);

		this.addAbility(new BrainJuicer(state));

		this.addAbility(new WizardsPrintsBrokenCards(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
