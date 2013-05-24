package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Urge to Feed")
@Types({Type.INSTANT})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class UrgetoFeed extends Card
{
	public UrgetoFeed(GameState state)
	{
		super(state);
		SetGenerator t = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		// Target creature gets -3/-3 until end of turn.
		this.addEffect(createFloatingEffect("Target creature gets -3/-3 until end of turn.", modifyPowerAndToughness(t, -3, -3)));

		SetGenerator yourVampires = Intersect.instance(HasSubType.instance(SubType.VAMPIRE), CREATURES_YOU_CONTROL);

		// You may tap any number of untapped Vampire creatures you control.

		EventFactory tapAnyNumber = new EventFactory(EventType.TAP_CHOICE, "You may tap any number of untapped Vampire creatures you control.");
		tapAnyNumber.parameters.put(EventType.Parameter.CAUSE, This.instance());
		tapAnyNumber.parameters.put(EventType.Parameter.PLAYER, You.instance());
		tapAnyNumber.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, null));
		tapAnyNumber.parameters.put(EventType.Parameter.CHOICE, yourVampires);
		this.addEffect(tapAnyNumber);

		SetGenerator thoseVampires = EffectResult.instance(tapAnyNumber);

		// If you do, put a +1/+1 counter on each of those Vampires.
		this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, thoseVampires, "If you do, put a +1/+1 counter on each of those Vampires."));
	}
}
