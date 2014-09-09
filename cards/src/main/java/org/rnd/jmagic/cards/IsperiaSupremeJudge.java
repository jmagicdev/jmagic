package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Isperia, Supreme Judge")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("2WWUU")
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class IsperiaSupremeJudge extends Card
{
	public static final class IsperiaSupremeJudgeAbility1 extends EventTriggeredAbility
	{
		public IsperiaSupremeJudgeAbility1(GameState state)
		{
			super(state, "Whenever a creature attacks you or a planeswalker you control, you may draw a card.");

			SetGenerator planeswalkersYouControl = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.PLANESWALKER));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, CreaturePermanents.instance());
			pattern.put(EventType.Parameter.DEFENDER, Union.instance(You.instance(), planeswalkersYouControl));
			this.addPattern(pattern);

			this.addEffect(youMay(drawACard()));
		}
	}

	public IsperiaSupremeJudge(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever a creature attacks you or a planeswalker you control, you
		// may draw a card.
		this.addAbility(new IsperiaSupremeJudgeAbility1(state));
	}
}
