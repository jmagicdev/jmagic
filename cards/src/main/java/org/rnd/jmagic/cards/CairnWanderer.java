package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cairn Wanderer")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class CairnWanderer extends Card
{
	private static class Filter extends SetGenerator
	{
		public static Filter instance(SetGenerator abilities, java.util.Set<Class<?>> toKeep)
		{
			return new Filter(abilities, toKeep);
		}

		private SetGenerator abilities;
		private java.util.Set<Class<?>> toKeep;

		private Filter(SetGenerator abilities, java.util.Set<Class<?>> toKeep)
		{
			this.abilities = abilities;
			this.toKeep = toKeep;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set abilities = this.abilities.evaluate(state, thisObject);
			java.util.Map<String, Identified> ret = new java.util.HashMap<String, Identified>();

			for(Keyword a: abilities.getAll(Keyword.class))
				for(Class<?> c: this.toKeep)
					if(c.isAssignableFrom(a.getClass()))
						ret.put(a.getName(), a);

			return Set.fromCollection(ret.values());
		}
	}

	public static final class CairnWandererAbility1 extends StaticAbility
	{
		public CairnWandererAbility1(GameState state)
		{
			super(state, "As long as a creature card with flying is in a graveyard, Cairn Wanderer has flying. The same is true for fear, first strike, double strike, deathtouch, haste, landwalk, lifelink, protection, reach, trample, shroud, and vigilance.");

			java.util.Set<Class<?>> abilityClasses = new java.util.LinkedHashSet<Class<?>>();
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Flying.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Fear.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.FirstStrike.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.DoubleStrike.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Deathtouch.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Haste.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Landwalk.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Lifelink.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Protection.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Reach.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Trample.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Shroud.class);
			abilityClasses.add(org.rnd.jmagic.abilities.keywords.Vigilance.class);

			SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
			SetGenerator creatureCardsInGraveyards = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance(), inGraveyards);
			SetGenerator abilitiesOfCreatureCardsInGraveyards = AbilitiesOf.instance(creatureCardsInGraveyards);
			SetGenerator abilities = Filter.instance(abilitiesOfCreatureCardsInGraveyards, abilityClasses);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_ABILITIES_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, abilities);
			this.addEffectPart(part);
		}
	}

	public CairnWanderer(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Changeling (This card is every creature type at all times.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Changeling(state));

		// As long as a creature card with flying is in a graveyard, Cairn
		// Wanderer has flying. The same is true for fear, first strike, double
		// strike, deathtouch, haste, landwalk, lifelink, protection, reach,
		// trample, shroud, and vigilance.
		this.addAbility(new CairnWandererAbility1(state));
	}
}
