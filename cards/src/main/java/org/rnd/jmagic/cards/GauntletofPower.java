package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Gauntlet of Power")
@Types({Type.ARTIFACT})
@ManaCost("5")
@ColorIdentity({})
public final class GauntletofPower extends Card
{
	public static final class GauntletofPowerAbility0 extends org.rnd.jmagic.abilityTemplates.AsThisEntersTheBattlefieldChooseAColor
	{
		public GauntletofPowerAbility0(GameState state)
		{
			super(state, "Gauntlet of Power");
			Linkable.Manager linkManager = this.getLinkManager();
			linkManager.addLinkClass(GauntletofPowerAbility1.class);
			linkManager.addLinkClass(GauntletofPowerAbility2.class);
		}
	}

	public static final class GauntletofPowerAbility1 extends StaticAbility
	{
		public GauntletofPowerAbility1(GameState state)
		{
			super(state, "Creatures of the chosen color get +1/+1.");
			this.getLinkManager().addLinkClass(GauntletofPowerAbility0.class);

			SetGenerator chosenColor = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			SetGenerator creaturesOfTheChosenColor = Intersect.instance(CreaturePermanents.instance(), HasColor.instance(chosenColor));
			this.addEffectPart(modifyPowerAndToughness(creaturesOfTheChosenColor, +1, +1));
		}
	}

	public static final class GauntletofPowerAbility2 extends EventTriggeredAbility
	{
		private static class TappedForManaOfAColor implements SetPattern
		{
			private SetPattern what;
			private SetPattern colors;

			public TappedForManaOfAColor(SetGenerator what, SetGenerator colors)
			{
				this.what = new SimpleSetPattern(what);
				this.colors = new SimpleSetPattern(colors);
			}

			@Override
			public void freeze(GameState state, Identified thisObject)
			{
				// Nothing to freeze
			}

			@Override
			public boolean match(GameState state, Identified thisObject, Set set)
			{
				// BECOMES_PLAYED.OBJECT is guaranteed to
				// only have one object in it.
				GameObject object = set.getOne(GameObject.class);
				if(!object.isManaAbility())
					return false;
				if(!object.isActivatedAbility())
					return false;

				ActivatedAbility ability = (ActivatedAbility)object;
				if(!ability.costsTap)
					return false;

				if(!this.what.match(state, thisObject, new Set(ability.getSource(state))))
					return false;

				for(ManaSymbol m: ability.getManaAdded())
				{
					if(this.colors.match(state, thisObject, Set.fromCollection(m.getColors())))
						return true;
				}
				return false;
			}
		}

		public GauntletofPowerAbility2(GameState state)
		{
			super(state, "Whenever a basic land is tapped for mana of the chosen color, its controller adds one mana of that color to his or her mana pool.");
			this.getLinkManager().addLinkClass(GauntletofPowerAbility0.class);

			SetGenerator basicLands = Intersect.instance(HasSuperType.instance(SuperType.BASIC), LandPermanents.instance());
			SetGenerator chosenColor = ChosenFor.instance(LinkedTo.instance(This.instance()));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.matchesManaAbilities = true;
			pattern.put(EventType.Parameter.OBJECT, new TappedForManaOfAColor(basicLands, chosenColor));
			pattern.put(EventType.Parameter.PLAYER, Players.instance());
			this.addPattern(pattern);

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator tappedLand = EventParameter.instance(triggerEvent, EventType.Parameter.OBJECT);
			SetGenerator itsController = ControllerOf.instance(tappedLand);

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Its controller adds one mana of that color to his or her mana pool.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, chosenColor);
			addMana.parameters.put(EventType.Parameter.PLAYER, itsController);
			this.addEffect(addMana);
		}
	}

	public GauntletofPower(GameState state)
	{
		super(state);

		// As Gauntlet of Power enters the battlefield, choose a color.
		this.addAbility(new GauntletofPowerAbility0(state));

		// Creatures of the chosen color get +1/+1.
		this.addAbility(new GauntletofPowerAbility1(state));

		// Whenever a basic land is tapped for mana of the chosen color, its
		// controller adds one mana of that color to his or her mana pool (in
		// addition to the mana the land produces).
		this.addAbility(new GauntletofPowerAbility2(state));
	}
}
