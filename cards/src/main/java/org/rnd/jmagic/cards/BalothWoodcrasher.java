package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Baloth Woodcrasher")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BalothWoodcrasher extends Card
{
	public static final class AlwaysCampWithAtLeastOnePersonWhoRunsSlowerThanYou extends EventTriggeredAbility
	{
		public AlwaysCampWithAtLeastOnePersonWhoRunsSlowerThanYou(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, Baloth Woodcrasher gets +4/+4 and gains trample until end of turn.");
			this.addPattern(landfall());
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +4, +4, "Baloth Woodcrasher gets +4/+4 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public BalothWoodcrasher(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new AlwaysCampWithAtLeastOnePersonWhoRunsSlowerThanYou(state));
	}
}
