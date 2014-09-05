package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gremlin Mine")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class GremlinMine extends Card
{
	public static final class GremlinMineAbility0 extends ActivatedAbility
	{
		public GremlinMineAbility0(GameState state)
		{
			super(state, "(1), (T), Sacrifice Gremlin Mine: Gremlin Mine deals 4 damage to target artifact creature.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Gremlin Mine"));
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact creature"));
			this.addEffect(permanentDealDamage(4, target, "Gremlin Mine deals 4 damage to target artifact creature."));
		}
	}

	public static final class GremlinMineAbility1 extends ActivatedAbility
	{
		public GremlinMineAbility1(GameState state)
		{
			super(state, "(1), (T), Sacrifice Gremlin Mine: Remove up to four charge counters from target noncreature artifact.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Gremlin Mine"));
			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target noncreature artifact"));
			EventFactory factory = new EventFactory(EventType.REMOVE_COUNTERS_CHOICE, "Remove up to four charge counters from target noncreature artifact.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.COUNTER, CountersOn.instance(target, Counter.CounterType.CHARGE));
			factory.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 4));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public GremlinMine(GameState state)
	{
		super(state);

		// (1), (T), Sacrifice Gremlin Mine: Gremlin Mine deals 4 damage to
		// target artifact creature.
		this.addAbility(new GremlinMineAbility0(state));

		// (1), (T), Sacrifice Gremlin Mine: Remove up to four charge counters
		// from target noncreature artifact.
		this.addAbility(new GremlinMineAbility1(state));
	}
}
