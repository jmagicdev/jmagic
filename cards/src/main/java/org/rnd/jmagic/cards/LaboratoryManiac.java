package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Laboratory Maniac")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class LaboratoryManiac extends Card
{
	public static final class LaboratoryManiacAbility0 extends StaticAbility
	{
		public LaboratoryManiacAbility0(GameState state)
		{
			super(state, "If you would draw a card while your library has no cards in it, you win the game instead.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			pattern.put(EventType.Parameter.PLAYER, You.instance());

			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "If you would draw a card while your library has no cards in it, you win the game instead.", pattern);
			replacement.makeOptional(You.instance());

			EventFactory factory = new EventFactory(EventType.WIN_GAME, "You win the game.");
			factory.parameters.put(EventType.Parameter.CAUSE, EventParameter.instance(replacement.replacedByThis(), EventType.Parameter.CAUSE));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = Both.instance(this.canApply, Intersect.instance(Count.instance(InZone.instance(LibraryOf.instance(You.instance()))), numberGenerator(0)));
		}
	}

	public LaboratoryManiac(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// If you would draw a card while your library has no cards in it, you
		// win the game instead.
		this.addAbility(new LaboratoryManiacAbility0(state));
	}
}
