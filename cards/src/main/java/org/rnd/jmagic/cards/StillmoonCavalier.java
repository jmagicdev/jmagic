package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Stillmoon Cavalier")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.KNIGHT})
@ManaCost("1(W/B)(W/B)")
@Printings({@Printings.Printed(ex = Eventide.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class StillmoonCavalier extends Card
{
	public static final class StillmoonCavalierAbility1 extends ActivatedAbility
	{
		public StillmoonCavalierAbility1(GameState state)
		{
			super(state, "(w/b): Stillmoon Cavalier gains flying until end of turn.");
			this.setManaCost(new ManaPool("(w/b)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Stillmoon Cavalier gains flying until end of turn."));
		}
	}

	public static final class StillmoonCavalierAbility2 extends ActivatedAbility
	{
		public StillmoonCavalierAbility2(GameState state)
		{
			super(state, "(w/b): Stillmoon Cavalier gains first strike until end of turn.");
			this.setManaCost(new ManaPool("(w/b)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "Stillmoon Cavalier gains first strike until end of turn."));
		}
	}

	public static final class StillmoonCavalierAbility3 extends ActivatedAbility
	{
		public StillmoonCavalierAbility3(GameState state)
		{
			super(state, "(w/b)(w/b): Stillmoon Cavalier gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(w/b)(w/b)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Stillmoon Cavalier gets +1/+0 until end of turn."));
		}
	}

	public StillmoonCavalier(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Protection from white and from black
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, HasColor.instance(Color.WHITE, Color.BLACK), "white and from black"));

		// (w/b): Stillmoon Cavalier gains flying until end of turn.
		this.addAbility(new StillmoonCavalierAbility1(state));

		// (w/b): Stillmoon Cavalier gains first strike until end of turn.
		this.addAbility(new StillmoonCavalierAbility2(state));

		// (w/b)(w/b): Stillmoon Cavalier gets +1/+0 until end of turn.
		this.addAbility(new StillmoonCavalierAbility3(state));
	}
}
