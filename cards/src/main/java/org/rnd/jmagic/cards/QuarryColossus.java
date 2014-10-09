package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quarry Colossus")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("5WW")
@ColorIdentity({Color.WHITE})
public final class QuarryColossus extends Card
{
	public static final class QuarryColossusAbility0 extends EventTriggeredAbility
	{
		public QuarryColossusAbility0(GameState state)
		{
			super(state, "When Quarry Colossus enters the battlefield, put target creature into its owner's library just beneath the top X cards of that library, where X is the number of Plains you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			SetGenerator yourPlains = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.PLAINS));
			SetGenerator X = Count.instance(yourPlains);

			EventFactory f = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target creature into its owner's library just beneath the top X cards of that library, where X is the number of Plains you control.");
			f.parameters.put(EventType.Parameter.CAUSE, This.instance());
			f.parameters.put(EventType.Parameter.INDEX, X);
			f.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(f);
		}
	}

	public QuarryColossus(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);

		// When Quarry Colossus enters the battlefield, put target creature into
		// its owner's library just beneath the top X cards of that library,
		// where X is the number of Plains you control.
		this.addAbility(new QuarryColossusAbility0(state));
	}
}
