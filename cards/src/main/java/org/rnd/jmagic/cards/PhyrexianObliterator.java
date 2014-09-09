package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phyrexian Obliterator")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("BBBB")
@ColorIdentity({Color.BLACK})
public final class PhyrexianObliterator extends Card
{
	public static final class PhyrexianObliteratorAbility1 extends EventTriggeredAbility
	{
		public PhyrexianObliteratorAbility1(GameState state)
		{
			super(state, "Whenever a source deals damage to Phyrexian Obliterator, that source's controller sacrifices that many permanents.");
			this.addPattern(whenIsDealtDamage(ABILITY_SOURCE_OF_THIS));
			SetGenerator triggerDamage = TriggerDamage.instance(This.instance());
			this.addEffect(sacrifice(ControllerOf.instance(SourceOfDamage.instance(triggerDamage)), Count.instance(triggerDamage), Permanents.instance(), "That source's controller sacrifices that many permanents."));
		}
	}

	public PhyrexianObliterator(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever a source deals damage to Phyrexian Obliterator, that
		// source's controller sacrifices that many permanents.
		this.addAbility(new PhyrexianObliteratorAbility1(state));
	}
}
