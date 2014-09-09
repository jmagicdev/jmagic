package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Emrakul, the Aeons Torn")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI})
@ManaCost("(15)")
@ColorIdentity({})
public final class EmrakultheAeonsTorn extends Card
{
	public static final class WhenYouCastTakeAnExtraTurn extends EventTriggeredAbility
	{
		public WhenYouCastTakeAnExtraTurn(GameState state)
		{
			super(state, "When you cast Emrakul, take an extra turn after this one.");
			this.triggersFromStack();

			this.addPattern(whenYouCastThisSpell());

			this.addEffect(takeExtraTurns(You.instance(), 1, "Take an extra turn after this one."));
		}
	}

	public EmrakultheAeonsTorn(GameState state)
	{
		super(state);

		this.setPower(15);
		this.setToughness(15);

		// Emrakul, the Aeons Torn can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// When you cast Emrakul, take an extra turn after this one.
		this.addAbility(new WhenYouCastTakeAnExtraTurn(state));

		// Flying, protection from colored spells, annihilator 6
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, Intersect.instance(HasColor.instance(Color.allColors()), Spells.instance()), "colored spells"));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Annihilator.Final(state, 6));

		// When Emrakul is put into a graveyard from anywhere, its owner
		// shuffles his or her graveyard into his or her library.
		this.addAbility(new org.rnd.jmagic.abilities.EldraziReshuffle(state, this.getName()));
	}
}
