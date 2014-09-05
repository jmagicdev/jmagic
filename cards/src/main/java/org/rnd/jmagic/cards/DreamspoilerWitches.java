package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dreamspoiler Witches")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.WIZARD})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DreamspoilerWitches extends Card
{
	public static final class TrickyMinuses extends org.rnd.jmagic.abilityTemplates.WhenYouCastASpellDuringOpponentsTurn
	{
		public TrickyMinuses(GameState state)
		{
			super(state, "you may have target creature get -1/-1 until end of turn.");
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			EventFactory minus = ptChangeUntilEndOfTurn(targetedBy(target), -1, -1, "Target creature gets -1/-1 until end of turn");
			this.addEffect(youMay(minus, "You may have target creature get -1/-1 until end of turn."));
		}
	}

	public DreamspoilerWitches(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever you cast a spell during an opponent's turn, you may have
		// target creature get -1/-1 until end of turn.
		this.addAbility(new TrickyMinuses(state));
	}
}
