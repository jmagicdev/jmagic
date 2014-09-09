package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Soul of the Harvest")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class SouloftheHarvest extends Card
{
	public static final class SouloftheHarvestAbility1 extends EventTriggeredAbility
	{
		public SouloftheHarvestAbility1(GameState state)
		{
			super(state, "Whenever another nontoken creature enters the battlefield under your control, you may draw a card.");
			SetGenerator nonTokenCreatures = RelativeComplement.instance(CreaturePermanents.instance(), Tokens.instance());
			SetGenerator other = RelativeComplement.instance(nonTokenCreatures, ABILITY_SOURCE_OF_THIS);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), other, You.instance(), false));

			this.addEffect(youMay(drawACard()));
		}
	}

	public SouloftheHarvest(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever another nontoken creature enters the battlefield under your
		// control, you may draw a card.
		this.addAbility(new SouloftheHarvestAbility1(state));
	}
}
