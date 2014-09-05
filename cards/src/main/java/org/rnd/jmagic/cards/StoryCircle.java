package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Story Circle")
@Types({Type.ENCHANTMENT})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class StoryCircle extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("StoryCircle", "Choose a source of damage of the chosen color.", true);

	public static final class ColorChoice extends org.rnd.jmagic.abilityTemplates.AsThisEntersTheBattlefieldChooseAColor
	{
		public ColorChoice(GameState state)
		{
			super(state, "Story Circle");

			this.getLinkManager().addLinkClass(ColorPrevention.class);
		}
	}

	public static final class ColorPrevention extends ActivatedAbility
	{
		public static final class StoryCircleEffect extends DamageReplacementEffect
		{
			private final Color color;
			private final int chosenSourceID;

			public StoryCircleEffect(Game game, String name, Color color, int chosenSourceID)
			{
				super(game, name);
				this.color = color;
				this.chosenSourceID = chosenSourceID;
				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				Player you = ((GameObject)this.getSourceObject(context.game.actualState)).getController(context.game.actualState);

				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				for(DamageAssignment assignment: damageAssignments)
				{
					GameObject source = context.state.get(assignment.sourceID);
					if(source.getColors().contains(this.color) && assignment.sourceID == this.chosenSourceID && assignment.takerID == you.ID)
						ret.add(assignment);
				}
				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				damageAssignments.clear();
				return new java.util.LinkedList<EventFactory>();
			}
		}

		/**
		 * @eparam CAUSE: the ability creating this shield
		 * @eparam COLOR: the color the source is chosen from
		 * @eparam PLAYER: the player to create the shield around
		 * @eparam RESULT: empty
		 */
		public static final EventType CHOOSE_A_SOURCE = new EventType("CHOOSE_A_SOURCE")
		{
			@Override
			public EventType.Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<EventType.Parameter, Set> parameters)
			{
				Player you = parameters.get(EventType.Parameter.PLAYER).getOne(Player.class);
				final Color color = parameters.get(EventType.Parameter.CHOICE).getOne(Color.class);

				SetGenerator validChoices = Intersect.instance(HasColor.instance(Identity.instance(color).noTextChanges()), AllSourcesOfDamage.instance());

				java.util.List<GameObject> chosen = you.sanitizeAndChoose(game.actualState, 1, validChoices.evaluate(game.actualState, null).getAll(GameObject.class), PlayerInterface.ChoiceType.DAMAGE_SOURCE, REASON);
				GameObject chosenSource = chosen.iterator().next();
				final int chosenSourceID = chosenSource.ID;

				DamageReplacementEffect replacement = new StoryCircleEffect(game, "The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage.", color, chosenSourceID);

				ContinuousEffect.Part part = replacementEffectPart(replacement);

				java.util.Map<EventType.Parameter, Set> floaterParameters = new java.util.HashMap<EventType.Parameter, Set>();
				floaterParameters.put(EventType.Parameter.CAUSE, parameters.get(EventType.Parameter.CAUSE));
				floaterParameters.put(EventType.Parameter.EFFECT, new Set(part));
				floaterParameters.put(EventType.Parameter.USES, ONE);
				Event floaterEvent = createEvent(game, "The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, floaterParameters);
				boolean ret = floaterEvent.perform(event, true);

				event.setResult(Empty.set);

				return ret;
			}
		};

		public ColorPrevention(GameState state)
		{
			super(state, "(W): The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage.");

			this.getLinkManager().addLinkClass(ColorChoice.class);

			this.setManaCost(new ManaPool("W"));

			SetGenerator chosenColor = ChosenFor.instance(LinkedTo.instance(This.instance()));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.CHOICE, chosenColor);
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(CHOOSE_A_SOURCE, parameters, "The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage."));
		}
	}

	public StoryCircle(GameState state)
	{
		super(state);

		this.addAbility(new ColorChoice(state));
		this.addAbility(new ColorPrevention(state));
	}
}
