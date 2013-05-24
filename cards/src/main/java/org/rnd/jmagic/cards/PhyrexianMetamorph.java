package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phyrexian Metamorph")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("3(U/P)")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class PhyrexianMetamorph extends Card
{
	/**
	 * @eparam CAUSE: what is creating the copy effect
	 * @eparam OBJECT: the object to create the copy effect on
	 * @eparam PLAYER: the player who will make the choice
	 * @eparam SOURCE: the objects to choose from for the copy
	 * @eparam RESULT: empty
	 */
	public static final EventType PHYREXIAN_COPY = new EventType("PHYREXIAN_COPY")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject placeCopyEffectOn = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			Player chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);

			PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(1, 1, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.COPY);
			chooseParameters.thisID = placeCopyEffectOn.ID;
			java.util.List<?> choice = chooser.sanitizeAndChoose(game.actualState, parameters.get(Parameter.SOURCE), chooseParameters);

			GameObject createCopyEffectFrom = new Set(choice).getOne(GameObject.class);

			if(createCopyEffectFrom != null)
			{
				ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
				part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(placeCopyEffectOn));
				part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, IdentifiedWithID.instance(createCopyEffectFrom.ID));
				part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.ARTIFACT));

				java.util.Map<Parameter, Set> effectParameters = new java.util.HashMap<Parameter, Set>();
				effectParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				effectParameters.put(EventType.Parameter.EFFECT, new Set(part));
				effectParameters.put(EventType.Parameter.EXPIRES, new Set(Empty.instance()));
				Event copy = createEvent(game, placeCopyEffectOn + " copies " + choice + ".", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, effectParameters);
				copy.perform(event, false);
			}

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class PhyrexianMetamorphAbility0 extends StaticAbility
	{
		public PhyrexianMetamorphAbility0(GameState state)
		{
			super(state, "You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "Phyrexian Metamorph enters the battlefield as a copy of any artifact or creature on the battlefield.");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator replacedMove = replacement.replacedByThis();
			EventFactory copy = new EventFactory(PHYREXIAN_COPY, "Phyrexian Metamorph enters the battlefield as a copy of any artifact or creature on the battlefield.");
			copy.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(replacedMove));
			copy.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(replacedMove));
			copy.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(replacedMove));
			copy.parameters.put(EventType.Parameter.SOURCE, Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()));

			replacement.makeOptional(You.instance());
			replacement.addEffect(copy);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}
	}

	public PhyrexianMetamorph(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// You may have Phyrexian Metamorph enter the battlefield as a copy of
		// any artifact or creature on the battlefield, except it's an artifact
		// in addition to its other types.
		this.addAbility(new PhyrexianMetamorphAbility0(state));
	}
}
