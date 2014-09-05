package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sphinx-Bone Wand")
@Types({Type.ARTIFACT})
@ManaCost("7")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class SphinxBoneWand extends Card
{
	public static final class SphinxBoneWandAbility0 extends EventTriggeredAbility
	{
		public SphinxBoneWandAbility0(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell, you may put a charge counter on Sphinx-Bone Wand. If you do, Sphinx-Bone Wand deals damage equal to the number of charge counters on it to target creature or player.");

			// Whenever you cast an instant or sorcery spell,
			SimpleEventPattern cast = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			cast.put(EventType.Parameter.PLAYER, You.instance());
			cast.put(EventType.Parameter.OBJECT, HasType.instance(Type.INSTANT, Type.SORCERY));
			this.addPattern(cast);

			// you may put a charge counter on Sphinx-Bone Wand.
			EventFactory chargeCounter = putCountersOnThis(1, Counter.CounterType.CHARGE, "Sphinx-Bone Wand");
			EventFactory mayPutCounter = youMay(chargeCounter, "You may put a charge counter on Sphinx-Bone Wand");

			// If you do, Sphinx-Bone Wand deals damage equal to the number of
			// charge counters on it to target creature or player.
			SetGenerator counters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			EventFactory damage = permanentDealDamage(counters, target, "Sphinx-Bone Wand deals damage equal to the number of charge counters on it to target creature or player.");

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may put a charge counter on Sphinx-Bone Wand. If you do, Sphinx-Bone Wand deals damage equal to the number of charge counters on it to target creature or player.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayPutCounter));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(damage));
			this.addEffect(effect);
		}
	}

	public SphinxBoneWand(GameState state)
	{
		super(state);

		this.addAbility(new SphinxBoneWandAbility0(state));
	}
}
