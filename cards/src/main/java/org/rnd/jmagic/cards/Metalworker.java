package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Metalworker")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = UrzasDestiny.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class Metalworker extends Card
{
	public static final class MetalworkerAbility0 extends ActivatedAbility
	{
		public MetalworkerAbility0(GameState state)
		{
			super(state, "(T): Reveal any number of artifact cards in your hand. Add (2) to your mana pool for each card revealed this way.");
			this.costsTap = true;

			EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Reveal any number of artifact cards in your hand.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(HandOf.instance(You.instance()))));
			reveal.parameters.put(EventType.Parameter.NUMBER, Between.instance(numberGenerator(0), null));
			reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(reveal);

			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add (2) to your mana pool for each card revealed this way.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(2)")));
			mana.parameters.put(EventType.Parameter.NUMBER, Count.instance(EffectResult.instance(reveal)));
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(mana);
		}
	}

	public Metalworker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// (T): Reveal any number of artifact cards in your hand. Add (2) to
		// your mana pool for each card revealed this way.
		this.addAbility(new MetalworkerAbility0(state));
	}
}
