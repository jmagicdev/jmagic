package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mindshrieker")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.BIRD})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Mindshrieker extends Card
{
	public static final class MindshriekerAbility1 extends ActivatedAbility
	{
		public MindshriekerAbility1(GameState state)
		{
			super(state, "(2): Target player puts the top card of his or her library into his or her graveyard. Mindshrieker gets +X/+X until end of turn, where X is that card's converted mana cost.");
			this.setManaCost(new ManaPool("(2)"));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			EventFactory mill = millCards(target, 1, "Target player puts the top card of his or her library into his or her graveyard.");
			this.addEffect(mill);
			SetGenerator X = ConvertedManaCostOf.instance(NewObjectOf.instance(EffectResult.instance(mill)));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, X, X, "Mindshrieker gets +X/+X until end of turn, where X is that card's converted mana cost."));
		}
	}

	public Mindshrieker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (2): Target player puts the top card of his or her library into his
		// or her graveyard. Mindshrieker gets +X/+X until end of turn, where X
		// is that card's converted mana cost.
		this.addAbility(new MindshriekerAbility1(state));
	}
}
