package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Searchlight Geist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SearchlightGeist extends Card
{
	public static final class SearchlightGeistAbility1 extends ActivatedAbility
	{
		public SearchlightGeistAbility1(GameState state)
		{
			super(state, "(3)(B): Searchlight Geist gains deathtouch until end of turn.");
			this.setManaCost(new ManaPool("(3)(B)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Deathtouch.class, "Searchlight Geist gains deathtouch until end of turn."));
		}
	}

	public SearchlightGeist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (3)(B): Searchlight Geist gains deathtouch until end of turn. (Any
		// amount of damage it deals to a creature is enough to destroy it.)
		this.addAbility(new SearchlightGeistAbility1(state));
	}
}
