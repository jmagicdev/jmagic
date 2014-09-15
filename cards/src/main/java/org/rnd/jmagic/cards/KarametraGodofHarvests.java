package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Karametra, God of Harvests")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("3GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class KarametraGodofHarvests extends Card
{
	public static final class KarametraGodofHarvestsAbility1 extends StaticAbility
	{
		public KarametraGodofHarvestsAbility1(GameState state)
		{
			super(state, "As long as your devotion to green and white is less than seven, Karametra isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.GREEN, Color.WHITE));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class KarametraGodofHarvestsAbility2 extends EventTriggeredAbility
	{
		public KarametraGodofHarvestsAbility2(GameState state)
		{
			super(state, "Whenever you cast a creature spell, you may search your library for a Forest or Plains card, put it onto the battlefield tapped, then shuffle your library.");

			SimpleEventPattern youCastACreature = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			SetGenerator creatureSpells = Intersect.instance(HasType.instance(Type.CREATURE), Spells.instance());
			SetGenerator yourCreatureSpells = Intersect.instance(ControlledBy.instance(You.instance(), Stack.instance()), creatureSpells);
			youCastACreature.put(EventType.Parameter.OBJECT, yourCreatureSpells);
			this.addPattern(youCastACreature);

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Forest or Plains card, put it onto the battlefield tapped, then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
			factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.FOREST, SubType.PLAINS)));
			this.addEffect(factory);
		}
	}

	public KarametraGodofHarvests(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(7);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to green and white is less than seven,
		// Karametra isn't a creature.
		this.addAbility(new KarametraGodofHarvestsAbility1(state));

		// Whenever you cast a creature spell, you may search your library for a
		// Forest or Plains card, put it onto the battlefield tapped, then
		// shuffle your library.
		this.addAbility(new KarametraGodofHarvestsAbility2(state));
	}
}
