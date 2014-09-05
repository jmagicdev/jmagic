package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Stern Mentor")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SternMentor extends Card
{
	public static final class TapMill extends ActivatedAbility
	{
		public TapMill(GameState state)
		{
			super(state, "(T): Target player puts the top two cards of his or her library into his or her graveyard.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 2, "Target player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public SternMentor(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Stern Mentor is paired with another creature, each of
		// those creatures has
		// "(T): Target player puts the top two cards of his or her library into his or her graveyard."
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Stern Mentor is paired with another creature, each of those creatures has \"(T): Target player puts the top two cards of his or her library into his or her graveyard.\"", TapMill.class));
	}
}
