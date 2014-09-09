package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Nissa's Chosen")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ELF})
@ManaCost("GG")
@ColorIdentity({Color.GREEN})
public final class NissasChosen extends Card
{
	public static final class GoToBottom extends StaticAbility
	{
		public GoToBottom(GameState state)
		{
			super(state, "If Nissa's Chosen would be put into a graveyard from the battlefield, put it on the bottom of its owner's library instead.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "If Nissa's Chosen would be put into a graveyard from the battlefield, put it on the bottom of its owner's library instead.");
			replacement.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), This.instance(), true));
			replacement.changeDestination(LibraryOf.instance(OwnerOf.instance(This.instance())), -1);

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public NissasChosen(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// If Nissa's Chosen would be put into a graveyard from the battlefield,
		// put it on the bottom of its owner's library instead.
		this.addAbility(new GoToBottom(state));
	}
}
