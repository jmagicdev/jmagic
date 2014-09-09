package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Kor Spiritdancer")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.KOR})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class KorSpiritdancer extends Card
{
	public static final class AuraPump extends StaticAbility
	{
		public AuraPump(GameState state)
		{
			super(state, "Kor Spiritdancer gets +2/+2 for each Aura attached to it.");

			SetGenerator pump = Multiply.instance(numberGenerator(2), Count.instance(Intersect.instance(AttachedTo.instance(This.instance()), HasSubType.instance(SubType.AURA))));

			this.addEffectPart(modifyPowerAndToughness(This.instance(), pump, pump));
		}
	}

	public static final class AuraDraw extends EventTriggeredAbility
	{
		public AuraDraw(GameState state)
		{
			super(state, "Whenever you cast an Aura spell, you may draw a card.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(HasSubType.instance(SubType.AURA));
			this.addPattern(pattern);

			this.addEffect(youMay(drawACard(), "You may draw a card."));
		}
	}

	public KorSpiritdancer(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		// Kor Spiritdancer gets +2/+2 for each Aura attached to it.
		this.addAbility(new AuraPump(state));

		// Whenever you cast an Aura spell, you may draw a card.
		this.addAbility(new AuraDraw(state));
	}
}
