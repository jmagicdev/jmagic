package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Graverobber Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("3G")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class GraverobberSpider extends Card
{
	public static final class GraverobberSpiderAbility1 extends ActivatedAbility
	{
		public GraverobberSpiderAbility1(GameState state)
		{
			super(state, "(3)(B): Graverobber Spider gets +X/+X until end of turn, where X is the number of creature cards in your graveyard. Activate this ability only once each turn.");
			this.setManaCost(new ManaPool("(3)(B)"));

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator amount = Count.instance(deadThings);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, amount, amount, "Graverobber Spider gets +X/+X until end of turn, where X is the number of creature cards in your graveyard."));

			this.perTurnLimit(1);
		}
	}

	public GraverobberSpider(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Reach
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// (3)(B): Graverobber Spider gets +X/+X until end of turn, where X is
		// the number of creature cards in your graveyard. Activate this ability
		// only once each turn.
		this.addAbility(new GraverobberSpiderAbility1(state));
	}
}
