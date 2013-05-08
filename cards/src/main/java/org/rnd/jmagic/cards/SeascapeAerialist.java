package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Seascape Aerialist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.ALLY, SubType.MERFOLK})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SeascapeAerialist extends Card
{
	public static final class FlyingAllies extends EventTriggeredAbility
	{
		public FlyingAllies(GameState state)
		{
			super(state, "Whenever Seascape Aerialist or another Ally enters the battlefield under your control, you may have Ally creatures you control gain flying until end of turn.");

			this.addPattern(allyTrigger());

			EventFactory abilityFactory = addAbilityUntilEndOfTurn(ALLY_CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Flying.class, "Ally creatures you control gain flying until end of turn");
			this.addEffect(youMay(abilityFactory, "You may have Ally creatures you control gain flying until end of turn."));
		}
	}

	public SeascapeAerialist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new FlyingAllies(state));
	}
}
